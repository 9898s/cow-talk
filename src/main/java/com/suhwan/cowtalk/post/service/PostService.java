package com.suhwan.cowtalk.post.service;

import com.suhwan.cowtalk.category.entity.Category;
import com.suhwan.cowtalk.category.repository.CategoryRepository;
import com.suhwan.cowtalk.common.security.SecurityUtil;
import com.suhwan.cowtalk.member.entity.Member;
import com.suhwan.cowtalk.member.repository.MemberRepository;
import com.suhwan.cowtalk.post.entity.Post;
import com.suhwan.cowtalk.post.entity.PostView;
import com.suhwan.cowtalk.post.model.PostDto;
import com.suhwan.cowtalk.post.model.UpdatePostRequest;
import com.suhwan.cowtalk.post.model.WritePostRequest;
import com.suhwan.cowtalk.post.repository.PostRepository;
import com.suhwan.cowtalk.post.repository.PostViewRepository;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;
import javax.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class PostService {

  private final PostRepository postRepository;
  private final MemberRepository memberRepository;
  private final CategoryRepository categoryRepository;
  private final PostViewRepository postViewRepository;

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

  // 게시글 조회
  @Transactional
  public PostDto getPost(Long id, HttpServletRequest request) {
    Post post = postRepository.findById(id)
        .orElseThrow(() -> new IllegalStateException("찾을 수 없는 게시글 번호입니다."));

    // 게시글 번호와 아이피가 redis 서버에 존재하지 않을 경우 조회수 증가
    String ip = request.getRemoteAddr();
    String postViewId = id + ":" + ip;
    if (!postViewRepository.existsById(postViewId)) {

      postViewRepository.save(
          PostView.builder()
              .id(postViewId)
              .postId(id)
              .ip(ip)
              .build()
      );

      post.addView();
    }
    return PostDto.fromEntity(post);
  }

  // 게시글 업데이트
  @Transactional
  public PostDto updatePost(Long id, UpdatePostRequest request) {
    Post post = postRepository.findById(id)
        .orElseThrow(() -> new IllegalStateException("찾을 수 없는 게시글 번호입니다."));

    String email = SecurityUtil.getLoginMemberEmail();
    Member member = memberRepository.findByEmail(email)
        .orElseThrow(() -> new IllegalStateException("찾을 수 없는 멤버 이메일입니다."));

    if (post.getMember() != member) {
      throw new IllegalStateException("작성한 게시글이 아닙니다.");
    }

    if (Boolean.TRUE.equals(post.getIsBlindYn()) || post.getDeleteDateTime() != null) {
      throw new IllegalStateException("수정할 수 없는 게시글입니다.");
    }

    post.update(request.getTitle(), request.getContent());

    return PostDto.fromEntity(post);
  }

  // 게시글 삭제
  @Transactional
  public PostDto deletePost(Long id) {
    Post post = postRepository.findById(id)
        .orElseThrow(() -> new IllegalStateException("찾을 수 없는 게시글 번호입니다."));

    if (post.getDeleteDateTime() != null) {
      throw new IllegalStateException("이미 삭제된 게시글입니다.");
    }

    String email = SecurityUtil.getLoginMemberEmail();
    Member member = memberRepository.findByEmail(email)
        .orElseThrow(() -> new IllegalStateException("찾을 수 없는 멤버 이메일입니다."));

    if (post.getMember() != member) {
      throw new IllegalStateException("작성한 게시글이 아닙니다.");
    }

    post.delete();
    return PostDto.fromEntity(post);
  }

  // 게시글 전체
  @Transactional(readOnly = true)
  public List<PostDto> getAllPost(int page, int size) {
    Sort sort = Sort.by(Sort.Direction.DESC, "id");
    Pageable pageable = PageRequest.of(page, size, sort);
    Page<Post> posts = postRepository.findAll(pageable);

    return posts.stream()
        .map(PostDto::fromEntity)
        .collect(Collectors.toList());
  }

  // 인기 게시글
  @Transactional(readOnly = true)
  public List<PostDto> hotPost(
      int page, int size, LocalDateTime startDateTime, LocalDateTime endDateTime) {
    Sort sort = Sort.by(Direction.DESC, "view");
    Pageable pageable = PageRequest.of(page, size, sort);
    Page<Post> posts = postRepository.findAllByCreateDateTimeBetween(pageable, startDateTime,
        endDateTime);

    return posts.stream()
        .map(PostDto::fromEntity)
        .collect(Collectors.toList());
  }
}
