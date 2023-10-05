package com.suhwan.cowtalk.post.service;

import static com.suhwan.cowtalk.common.type.ErrorCode.ALREADY_DELETE_POST;
import static com.suhwan.cowtalk.common.type.ErrorCode.INVALID_CATEGORY_ID;
import static com.suhwan.cowtalk.common.type.ErrorCode.INVALID_MEMBER_EMAIL;
import static com.suhwan.cowtalk.common.type.ErrorCode.INVALID_POST_ID;
import static com.suhwan.cowtalk.common.type.ErrorCode.NOT_OWN_POST;
import static com.suhwan.cowtalk.common.type.ErrorCode.READ_ONLY_CATEGORY;

import com.suhwan.cowtalk.category.entity.Category;
import com.suhwan.cowtalk.category.repository.CategoryRepository;
import com.suhwan.cowtalk.common.exception.CategoryException;
import com.suhwan.cowtalk.common.exception.MemberException;
import com.suhwan.cowtalk.common.exception.PostException;
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
        .orElseThrow(() -> new CategoryException(INVALID_CATEGORY_ID));

    if (Boolean.TRUE.equals(category.isReadOnly())) {
      throw new CategoryException(READ_ONLY_CATEGORY);
    }

    String email = SecurityUtil.getLoginMemberEmail();
    Member member = memberRepository.findByEmail(email)
        .orElseThrow(() -> new MemberException(INVALID_MEMBER_EMAIL));

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
        .orElseThrow(() -> new PostException(INVALID_POST_ID));

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
        .orElseThrow(() -> new PostException(INVALID_POST_ID));

    String email = SecurityUtil.getLoginMemberEmail();
    Member member = memberRepository.findByEmail(email)
        .orElseThrow(() -> new MemberException(INVALID_MEMBER_EMAIL));

    if (post.getMember() != member) {
      throw new PostException(NOT_OWN_POST);
    }

    post.update(request.getTitle(), request.getContent());

    return PostDto.fromEntity(post);
  }

  // 게시글 삭제
  @Transactional
  public PostDto deletePost(Long id) {
    Post post = postRepository.findById(id)
        .orElseThrow(() -> new PostException(INVALID_POST_ID));

    if (post.getDeleteDateTime() != null) {
      throw new PostException(ALREADY_DELETE_POST);
    }

    String email = SecurityUtil.getLoginMemberEmail();
    Member member = memberRepository.findByEmail(email)
        .orElseThrow(() -> new MemberException(INVALID_MEMBER_EMAIL));

    if (post.getMember() != member) {
      throw new PostException(NOT_OWN_POST);
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

  // 게시글 전체(카테고리)
  @Transactional(readOnly = true)
  public List<PostDto> getAllCategoryPost(Long categoryId, int page, int size) {
    Category category = categoryRepository.findById(categoryId)
        .orElseThrow(() -> new CategoryException(INVALID_CATEGORY_ID));

    Sort sort = Sort.by(Sort.Direction.DESC, "id");
    Pageable pageable = PageRequest.of(page, size, sort);
    Page<Post> posts = postRepository.findAllByCategory(category, pageable);

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
