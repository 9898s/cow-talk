package com.suhwan.cowtalk.reply.service;

import static com.suhwan.cowtalk.common.type.ErrorCode.ALREADY_DELETE_REPLY;
import static com.suhwan.cowtalk.common.type.ErrorCode.INVALID_COMMENT_ID;
import static com.suhwan.cowtalk.common.type.ErrorCode.INVALID_MEMBER_EMAIL;
import static com.suhwan.cowtalk.common.type.ErrorCode.INVALID_REPLY_ID;
import static com.suhwan.cowtalk.common.type.ErrorCode.NOT_OWN_REPLY;

import com.suhwan.cowtalk.comment.entity.Comment;
import com.suhwan.cowtalk.comment.repository.CommentRepository;
import com.suhwan.cowtalk.common.exception.CommentException;
import com.suhwan.cowtalk.common.exception.MemberException;
import com.suhwan.cowtalk.common.exception.ReplyException;
import com.suhwan.cowtalk.common.security.SecurityUtil;
import com.suhwan.cowtalk.member.entity.Member;
import com.suhwan.cowtalk.member.repository.MemberRepository;
import com.suhwan.cowtalk.reply.entity.Reply;
import com.suhwan.cowtalk.reply.model.ReplyDto;
import com.suhwan.cowtalk.reply.model.UpdateReplyRequest;
import com.suhwan.cowtalk.reply.model.WriteReplyRequest;
import com.suhwan.cowtalk.reply.repository.ReplyRepository;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class ReplyService {

  private final ReplyRepository replyRepository;
  private final CommentRepository commentRepository;
  private final MemberRepository memberRepository;

  // 대댓글 쓰기
  public ReplyDto writeReply(WriteReplyRequest request) {
    Comment comment = commentRepository.findById(request.getCommentId())
        .orElseThrow(() -> new CommentException(INVALID_COMMENT_ID));

    String email = SecurityUtil.getLoginMemberEmail();
    Member member = memberRepository.findByEmail(email)
        .orElseThrow(() -> new MemberException(INVALID_MEMBER_EMAIL));

    return ReplyDto.fromEntity(
        replyRepository.save(
            Reply.builder()
                .content(request.getContent())
                .comment(comment)
                .member(member)
                .build()
        )
    );
  }

  // 댓글 대댓글 조회
  @Transactional(readOnly = true)
  public List<ReplyDto> getCommentReply(Long commentId) {
    Comment comment = commentRepository.findById(commentId)
        .orElseThrow(() -> new CommentException(INVALID_COMMENT_ID));

    List<Reply> replyList = replyRepository.findAllByComment(comment);

    return replyList.stream()
        .map(ReplyDto::fromEntity)
        .collect(Collectors.toList());
  }

  @Transactional
  public ReplyDto updateReply(Long id, UpdateReplyRequest request) {
    Reply reply = replyRepository.findById(id)
        .orElseThrow(() -> new ReplyException(INVALID_REPLY_ID));

    String email = SecurityUtil.getLoginMemberEmail();
    Member member = memberRepository.findByEmail(email)
        .orElseThrow(() -> new MemberException(INVALID_MEMBER_EMAIL));

    if (reply.getMember() != member) {
      throw new ReplyException(NOT_OWN_REPLY);
    }

    reply.update(request.getContent());

    return ReplyDto.fromEntity(reply);
  }

  // 대댓글 삭제
  @Transactional
  public ReplyDto deleteReply(Long id) {
    Reply reply = replyRepository.findById(id)
        .orElseThrow(() -> new ReplyException(INVALID_REPLY_ID));

    String email = SecurityUtil.getLoginMemberEmail();
    Member member = memberRepository.findByEmail(email)
        .orElseThrow(() -> new MemberException(INVALID_MEMBER_EMAIL));

    if (reply.getMember() != member) {
      throw new ReplyException(NOT_OWN_REPLY);
    }
    if (reply.getDeleteDateTime() != null) {
      throw new ReplyException(ALREADY_DELETE_REPLY);
    }

    reply.delete();

    return ReplyDto.fromEntity(reply);
  }
}
