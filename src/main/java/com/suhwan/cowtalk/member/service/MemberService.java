package com.suhwan.cowtalk.member.service;

import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.suhwan.cowtalk.common.component.MailComponent;
import com.suhwan.cowtalk.common.security.jwt.TokenProvider;
import com.suhwan.cowtalk.common.security.jwt.TokenRequest;
import com.suhwan.cowtalk.common.security.jwt.TokenResponse;
import com.suhwan.cowtalk.common.security.jwt.refreshToken.RefreshToken;
import com.suhwan.cowtalk.common.security.jwt.refreshToken.RefreshTokenRepository;
import com.suhwan.cowtalk.member.entity.Member;
import com.suhwan.cowtalk.member.model.MemberDto;
import com.suhwan.cowtalk.member.model.SignInMemberRequest;
import com.suhwan.cowtalk.member.model.SignUpMemberRequest;
import com.suhwan.cowtalk.member.model.UpdateMemberRequest;
import com.suhwan.cowtalk.member.repository.MemberRepository;
import com.suhwan.cowtalk.member.type.Roles;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

@Slf4j
@RequiredArgsConstructor
@Service
public class MemberService {

  private static final Long MEMBER_UPDATE_PERIOD_MONTH = 1L;

  private final MemberRepository memberRepository;
  private final RefreshTokenRepository refreshTokenRepository;

  private final BCryptPasswordEncoder bCryptPasswordEncoder;
  private final TokenProvider tokenProvider;
  private final MailComponent mailComponent;
  private final AmazonS3Client amazonS3Client;

  @Value("${cloud.aws.s3.bucket}")
  private String bucket;

  // 회원가입
  public MemberDto signUp(SignUpMemberRequest request) {
    if (memberRepository.existsByEmail(request.getEmail())) {
      throw new IllegalStateException("이미 사용중인 이메일입니다.");
    }
    if (memberRepository.existsByNickname(request.getNickname())) {
      throw new IllegalStateException("이미 사용중인 닉네임입니다.");
    }

    String uuid = UUID.randomUUID().toString();

    Member member = Member.builder()
        .email(request.getEmail())
        .password(bCryptPasswordEncoder.encode(request.getPassword()))
        .nickname(request.getNickname())
        .uuid(uuid)
        .emailAuthYn(false)
        .roles(Roles.ROLE_USER)
        .createDateTime(LocalDateTime.now())
        .build();

    String email = request.getEmail();
    String subject = "카우톡 사이트 가입을 축하드립니다.";
    String text = "<p>아래 링크를 클릭하셔서 가입을 완료하세요.</p>"
        + "<div>"
        + "<a target'_blank' href='http://localhost:8080/api/member/email-auth?uuid=" + uuid
        + "'>가입 완료</a>"
        + "</div>";
    mailComponent.sendMail(email, subject, text);

    return MemberDto.fromEntity(memberRepository.save(member));
  }

  // 로그인
  public MemberDto signIn(SignInMemberRequest request) {
    Member member = memberRepository.findByEmail(request.getEmail())
        .orElseThrow(() -> new IllegalStateException("가입된 이메일이 아닙니다."));

    if (!bCryptPasswordEncoder.matches(request.getPassword(), member.getPassword())) {
      throw new IllegalStateException("비밀번호가 일치하지 않습니다.");
    }

    if (!member.isEmailAuthYn()) {
      throw new IllegalStateException("인증된 회원이 아닙니다.");
    }

    return MemberDto.fromEntity(member);
  }

  // 인증 완료
  @Transactional
  public MemberDto emailAuth(String uuid) {
    Member member = memberRepository.findByUuid(uuid)
        .orElseThrow(() -> new IllegalStateException("찾을 수 없는 UUID입니다."));

    member.authorization();

    return MemberDto.fromEntity(member);
  }

  // 토큰 재발급
  public TokenResponse refresh(TokenRequest request) {
    RefreshToken refreshToken = refreshTokenRepository.findById(request.getRefreshToken())
        .orElseThrow(() -> new IllegalStateException("찾을 수 없는 리프레쉬 토큰입니다."));

    Member member = memberRepository.findByEmail(refreshToken.getMemberEmail())
        .orElseThrow(() -> new IllegalStateException("찾을 수 없는 회원 이메일입니다."));

    String accessToken = tokenProvider.createToken(refreshToken.getMemberEmail(),
        member.getRoles());

    return TokenResponse.of(accessToken, refreshToken.getRefreshToken());
  }

  // 회원 정보 수정
  @Transactional
  public MemberDto updateMember(Long id, UpdateMemberRequest request) {
    Member member = memberRepository.findById(id)
        .orElseThrow(() -> new IllegalStateException("찾을 수 없는 회원 번호입니다."));

    LocalDateTime updateDateTime = member.getUpdateDateTime();
    if (updateDateTime != null && updateDateTime.isBefore(
        updateDateTime.plusMonths(MEMBER_UPDATE_PERIOD_MONTH))) {
      throw new IllegalStateException("닉네임을 아직 변경할 수 없습니다.");
    }

    member.update(request.getNickname());

    return MemberDto.fromEntity(member);
  }

  // 회원 이미지 업로드
  @Transactional
  public MemberDto uploadMember(Long id, MultipartFile file) {
    Member member = memberRepository.findById(id)
        .orElseThrow(() -> new IllegalStateException("찾을 수 없는 회원 번호입니다."));

    ObjectMetadata metadata = new ObjectMetadata();
    metadata.setContentType(file.getContentType());
    metadata.setContentLength(file.getSize());

    String fileName = file.getOriginalFilename();

    String extension = getExtension(fileName);

    String fileUrl = member.getUuid() + "." + extension;
    String key = "profile/" + fileUrl;

    try {
      // 존재할 경우 삭제
      if (amazonS3Client.doesObjectExist(bucket, key)) {
        amazonS3Client.deleteObject(bucket, key);
      }

      amazonS3Client.putObject(bucket, key, file.getInputStream(), metadata);
      member.upload(key);
    } catch (IOException e) {
      e.printStackTrace();
    }

    return MemberDto.fromEntity(member);
  }

  // 확장자명 구하기
  private static String getExtension(String fileName) {
    int extensionIndex = fileName.lastIndexOf(".");
    return fileName.substring(extensionIndex + 1);
  }

  @Transactional(readOnly = true)
  public MemberDto getMember(Long id) {
    Member member = memberRepository.findById(id)
        .orElseThrow(() -> new IllegalStateException("찾을 수 없는 회원 번호입니다."));

    return MemberDto.fromEntity(member);
  }

  // 회원 삭제
  @Transactional
  public MemberDto deleteMember(Long id) {
    Member member = memberRepository.findById(id)
        .orElseThrow(() -> new IllegalStateException("찾을 수 없는 회원 번호입니다."));

    if (member.getDeleteDateTime() != null) {
      throw new IllegalStateException("이미 삭제된 회원 번호입니다.");
    }

    member.delete();

    return MemberDto.fromEntity(member);
  }
}
