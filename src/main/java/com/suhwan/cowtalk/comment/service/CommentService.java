package com.suhwan.cowtalk.comment.service;

import com.suhwan.cowtalk.comment.entity.Comment;
import com.suhwan.cowtalk.comment.model.CommentDto;
import com.suhwan.cowtalk.comment.model.UpdateCommentRequest;
import com.suhwan.cowtalk.comment.model.WriteCommentRequest;
import com.suhwan.cowtalk.comment.repository.CommentRepository;
import com.suhwan.cowtalk.common.security.SecurityUtil;
import com.suhwan.cowtalk.member.entity.Member;
import com.suhwan.cowtalk.member.repository.MemberRepository;
import com.suhwan.cowtalk.post.entity.Post;
import com.suhwan.cowtalk.post.repository.PostRepository;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class CommentService {

  private final CommentRepository commentRepository;
  private final PostRepository postRepository;
  private final MemberRepository memberRepository;

  // 댓글 작성
  public CommentDto writeComment(WriteCommentRequest request) {
    Post post = postRepository.findById(request.getPostId())
        .orElseThrow(() -> new IllegalStateException("찾을 수 없는 게시글 번호입니다."));

    String email = SecurityUtil.getLoginMemberEmail();
    Member member = memberRepository.findByEmail(email)
        .orElseThrow(() -> new IllegalStateException("찾을 수 없는 멤버 이메일입니다."));

    return CommentDto.fromEntity(
        commentRepository.save(
            Comment.builder()
                .content(request.getContent())
                .post(post)
                .member(member)
                .build()
        )
    );
  }

  // 게시글 댓글 조회
  @Transactional(readOnly = true)
  public List<CommentDto> getPostComment(Long postId) {
    Post post = postRepository.findById(postId)
        .orElseThrow(() -> new IllegalStateException("찾을 수 없는 게시글 번호입니다."));

    List<Comment> commentList = commentRepository.findAllByPost(post);

    return commentList.stream()
        .map(CommentDto::fromEntity)
        .collect(Collectors.toList());
  }

  // 댓글 수정
  @Transactional
  public CommentDto updateComment(Long id, UpdateCommentRequest request) {
    Comment comment = commentRepository.findById(id)
        .orElseThrow(() -> new IllegalStateException("찾을 수 없는 댓글 번호입니다."));

    String email = SecurityUtil.getLoginMemberEmail();
    Member member = memberRepository.findByEmail(email)
        .orElseThrow(() -> new IllegalStateException("찾을 수 없는 멤버 이메일입니다."));

    if (comment.getMember() != member) {
      throw new IllegalStateException("본인이 작성한 댓글이 아닙니다.");
    }

    comment.update(request.getContent());

    return CommentDto.fromEntity(comment);
  }

  // 댓글 삭제
  @Transactional
  public CommentDto deleteComment(Long id) {
    Comment comment = commentRepository.findById(id)
        .orElseThrow(() -> new IllegalStateException("찾을 수 없는 댓글 번호입니다."));

    String email = SecurityUtil.getLoginMemberEmail();
    Member member = memberRepository.findByEmail(email)
        .orElseThrow(() -> new IllegalStateException("찾을 수 없는 멤버 이메일입니다."));

    if (comment.getMember() != member) {
      throw new IllegalStateException("본인이 작성한 댓글이 아닙니다.");
    }
    if (comment.getDeleteDateTime() != null) {
      throw new IllegalStateException("이미 삭제된 댓글입니다.");
    }

    comment.delete();

    return CommentDto.fromEntity(comment);
  }
}
