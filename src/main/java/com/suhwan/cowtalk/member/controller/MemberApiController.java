package com.suhwan.cowtalk.member.controller;

import com.suhwan.cowtalk.common.security.jwt.TokenProvider;
import com.suhwan.cowtalk.common.security.jwt.TokenRequest;
import com.suhwan.cowtalk.common.security.jwt.TokenResponse;
import com.suhwan.cowtalk.member.model.AuthMemberResponse;
import com.suhwan.cowtalk.member.model.UpdateMemberResponse;
import com.suhwan.cowtalk.member.model.UpdateMemberRequest;
import com.suhwan.cowtalk.member.model.MemberDto;
import com.suhwan.cowtalk.member.model.SignInMemberRequest;
import com.suhwan.cowtalk.member.model.SignUpMemberRequest;
import com.suhwan.cowtalk.member.model.SignUpMemberResponse;
import com.suhwan.cowtalk.member.model.UploadMemberResponse;
import com.suhwan.cowtalk.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RequiredArgsConstructor
@RequestMapping("/api/member")
@RestController
public class MemberApiController {

  private final MemberService memberService;
  private final TokenProvider tokenProvider;

  @PostMapping("/signup")
  public ResponseEntity<?> signup(@RequestBody SignUpMemberRequest request) {
    MemberDto memberDto = memberService.signUp(request);

    return ResponseEntity.ok().body(SignUpMemberResponse.from(memberDto));
  }

  @PostMapping("/signin")
  public ResponseEntity<?> signin(@RequestBody SignInMemberRequest request) {
    MemberDto memberDto = memberService.signIn(request);

    String token = tokenProvider.createToken(memberDto.getEmail(), memberDto.getRoles());
    String refreshToken = tokenProvider.createRefreshToken(memberDto.getEmail());

    return ResponseEntity.ok().body(TokenResponse.of(token, refreshToken));
  }

  @GetMapping("/email-auth")
  public ResponseEntity<?> emailAuth(@RequestParam String uuid) {
    MemberDto memberDto = memberService.emailAuth(uuid);

    return ResponseEntity.ok().body(AuthMemberResponse.from(memberDto));
  }

  @GetMapping("/refresh")
  public ResponseEntity<?> refresh(@RequestBody TokenRequest request) {
    TokenResponse response = memberService.refresh(request);

    return ResponseEntity.ok().body(response);
  }

  @PutMapping("/{id}")
  public ResponseEntity<?> updateMember(@PathVariable Long id, @RequestBody UpdateMemberRequest request) {
    MemberDto memberDto = memberService.updateMember(id, request);

    return ResponseEntity.ok().body(UpdateMemberResponse.from(memberDto));
  }

  @PostMapping("/upload/{id}")
  public ResponseEntity<?> uploadMember(@PathVariable Long id, @RequestParam("file") MultipartFile file) {
    MemberDto memberDto = memberService.uploadMember(id, file);

    return ResponseEntity.ok().body(UploadMemberResponse.from(memberDto));
  }
}
