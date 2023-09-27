package com.suhwan.cowtalk.post.service;

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

@RequiredArgsConstructor
@Service
public class PostGoodBadService {

  private final PostGoodBadRepository postGoodBadRepository;
  private final PostRepository postRepository;
  private final MemberRepository memberRepository;

  // 게시글 좋아요/싫어요
  public PostGoodBadDto goodBadPost(Long id, GoodBad goodBad) {
    Post post = postRepository.findById(id)
        .orElseThrow(() -> new IllegalStateException("찾을 수 없는 게시글 번호입니다."));

    String email = SecurityUtil.getLoginMemberEmail();
    Member member = memberRepository.findByEmail(email)
        .orElseThrow(() -> new IllegalStateException("찾을 수 없는 멤버 이메일입니다."));

    if (post.getMember() == member) {
      throw new IllegalStateException("자신이 작성한 게시글에 좋아요를 누를 수 없습니다.");
    }

    if (postGoodBadRepository.existsByPostAndMember(post, member)) {
      throw new IllegalStateException("이미 좋아요 또는 싫어요를 누르셨습니다.");
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
    if (!postRepository.existsById(id)) {
      throw new IllegalStateException("찾을 수 없는 게시글 번호입니다.");
    }

    return postGoodBadRepository.countByGoodBad(goodBad);
  }
}