package com.suhwan.cowtalk.comment.service;

import static com.suhwan.cowtalk.common.type.ErrorCode.ALREADY_DELETE_COMMENT;
import static com.suhwan.cowtalk.common.type.ErrorCode.INVALID_COMMENT_ID;
import static com.suhwan.cowtalk.common.type.ErrorCode.INVALID_MEMBER_EMAIL;
import static com.suhwan.cowtalk.common.type.ErrorCode.INVALID_POST_ID;
import static com.suhwan.cowtalk.common.type.ErrorCode.NOT_OWN_COMMENT;

import com.suhwan.cowtalk.comment.entity.Comment;
import com.suhwan.cowtalk.comment.model.CommentDto;
import com.suhwan.cowtalk.comment.model.UpdateCommentRequest;
import com.suhwan.cowtalk.comment.model.WriteCommentRequest;
import com.suhwan.cowtalk.comment.repository.CommentRepository;
import com.suhwan.cowtalk.common.exception.CommentException;
import com.suhwan.cowtalk.common.exception.MemberException;
import com.suhwan.cowtalk.common.exception.PostException;
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
        .orElseThrow(() -> new PostException(INVALID_POST_ID));

    String email = SecurityUtil.getLoginMemberEmail();
    Member member = memberRepository.findByEmail(email)
        .orElseThrow(() -> new MemberException(INVALID_MEMBER_EMAIL));

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
        .orElseThrow(() -> new PostException(INVALID_POST_ID));

    List<Comment> commentList = commentRepository.findAllByPost(post);

    return commentList.stream()
        .map(CommentDto::fromEntity)
        .collect(Collectors.toList());
  }

  // 댓글 수정
  @Transactional
  public CommentDto updateComment(Long id, UpdateCommentRequest request) {
    Comment comment = commentRepository.findById(id)
        .orElseThrow(() -> new CommentException(INVALID_COMMENT_ID));

    String email = SecurityUtil.getLoginMemberEmail();
    Member member = memberRepository.findByEmail(email)
        .orElseThrow(() -> new MemberException(INVALID_MEMBER_EMAIL));

    if (comment.getMember() != member) {
      throw new CommentException(NOT_OWN_COMMENT);
    }

    comment.update(request.getContent());

    return CommentDto.fromEntity(comment);
  }

  // 댓글 삭제
  @Transactional
  public CommentDto deleteComment(Long id) {
    Comment comment = commentRepository.findById(id)
        .orElseThrow(() -> new CommentException(INVALID_COMMENT_ID));

    String email = SecurityUtil.getLoginMemberEmail();
    Member member = memberRepository.findByEmail(email)
        .orElseThrow(() -> new MemberException(INVALID_MEMBER_EMAIL));

    if (comment.getMember() != member) {
      throw new CommentException(NOT_OWN_COMMENT);
    }
    if (comment.getDeleteDateTime() != null) {
      throw new CommentException(ALREADY_DELETE_COMMENT);
    }

    comment.delete();

    return CommentDto.fromEntity(comment);
  }
}
