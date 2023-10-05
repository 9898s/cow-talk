package com.suhwan.cowtalk.post.service;

import static com.suhwan.cowtalk.common.type.ErrorCode.ALREADY_GOOD_BAD_POST;
import static com.suhwan.cowtalk.common.type.ErrorCode.CANNOT_GOOD_BAD_OWN_POST;
import static com.suhwan.cowtalk.common.type.ErrorCode.INVALID_MEMBER_EMAIL;
import static com.suhwan.cowtalk.common.type.ErrorCode.INVALID_POST_ID;

import com.suhwan.cowtalk.common.exception.MemberException;
import com.suhwan.cowtalk.common.exception.PostException;
import com.suhwan.cowtalk.common.security.SecurityUtil;
import com.suhwan.cowtalk.common.type.GoodBad;
import com.suhwan.cowtalk.member.entity.Member;
import com.suhwan.cowtalk.member.repository.MemberRepository;
import com.suhwan.cowtalk.post.entity.Post;
import com.suhwan.cowtalk.post.entity.PostGoodBad;
import com.suhwan.cowtalk.post.model.goodbad.PostGoodBadDto;
import com.suhwan.cowtalk.post.repository.PostGoodBadRepository;
import com.suhwan.cowtalk.post.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class PostGoodBadService {

  private static final int BLIND_COUNT = 5;

  private final PostGoodBadRepository postGoodBadRepository;
  private final PostRepository postRepository;
  private final MemberRepository memberRepository;

  // 게시글 좋아요/싫어요
  @Transactional
  public PostGoodBadDto goodBadPost(Long id, GoodBad goodBad) {
    Post post = postRepository.findById(id)
        .orElseThrow(() -> new PostException(INVALID_POST_ID));

    String email = SecurityUtil.getLoginMemberEmail();
    Member member = memberRepository.findByEmail(email)
        .orElseThrow(() -> new MemberException(INVALID_MEMBER_EMAIL));

    if (post.getMember() == member) {
      throw new PostException(CANNOT_GOOD_BAD_OWN_POST);
    }

    if (postGoodBadRepository.existsByPostAndMember(post, member)) {
      throw new PostException(ALREADY_GOOD_BAD_POST);
    }

    // 블라인드 처리
    if (!post.isBlind() &&
        postGoodBadRepository.countByPostAndGoodBad(post, GoodBad.BAD) >= BLIND_COUNT) {
      post.blind();
    }

    return PostGoodBadDto.fromEntity(
        postGoodBadRepository.save(
            PostGoodBad.builder()
                .post(post)
                .member(member)
                .goodBad(goodBad)
                .build()
        )
    );
  }

  // 게시글 좋아요/싫어요 개수 조회
  public Long countGoodBad(Long id, GoodBad goodBad) {
    Post post = postRepository.findById(id)
        .orElseThrow(() -> new PostException(INVALID_POST_ID));

    return postGoodBadRepository.countByPostAndGoodBad(post, goodBad);
  }
}