package com.suhwan.cowtalk.post.service;

import com.suhwan.cowtalk.category.entity.Category;
import com.suhwan.cowtalk.category.repository.CategoryRepository;
import com.suhwan.cowtalk.common.security.SecurityUtil;
import com.suhwan.cowtalk.member.entity.Member;
import com.suhwan.cowtalk.member.repository.MemberRepository;
import com.suhwan.cowtalk.post.entity.Post;
import com.suhwan.cowtalk.post.model.PostDto;
import com.suhwan.cowtalk.post.model.WritePostRequest;
import com.suhwan.cowtalk.post.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class PostService {

  private final PostRepository postRepository;
  private final MemberRepository memberRepository;
  private final CategoryRepository categoryRepository;

  // 게시글 작성
  public PostDto writePost(WritePostRequest request) {
    Category category = categoryRepository.findById(request.getCategoryId())
        .orElseThrow(() -> new IllegalStateException("찾을 수 없는 카테고리 번호입니다."));

    if (Boolean.TRUE.equals(category.getIsReadOnly())) {
      throw new IllegalStateException("읽기 전용 카테고리입니다.");
    }

    String email = SecurityUtil.getLoginMemberEmail();
    Member member = memberRepository.findByEmail(email)
        .orElseThrow(() -> new IllegalStateException("찾을 수 없는 멤버 이메일입니다."));

    return PostDto.fromEntity(
        postRepository.save(
            Post.builder()
                .category(category)
                .title(request.getTitle())
                .content(request.getContent())
                .member(member)
                .build()
        )
    );
  }
}
