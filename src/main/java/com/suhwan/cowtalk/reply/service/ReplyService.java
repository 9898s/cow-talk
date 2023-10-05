package com.suhwan.cowtalk.reply.service;

import com.suhwan.cowtalk.comment.entity.Comment;
import com.suhwan.cowtalk.comment.repository.CommentRepository;
import com.suhwan.cowtalk.common.security.SecurityUtil;
import com.suhwan.cowtalk.member.entity.Member;
import com.suhwan.cowtalk.member.repository.MemberRepository;
import com.suhwan.cowtalk.reply.entity.Reply;
import com.suhwan.cowtalk.reply.model.ReplyDto;
import com.suhwan.cowtalk.reply.model.UpdateReplyRequest;
import com.suhwan.cowtalk.reply.model.WriteReplyRequest;
import com.suhwan.cowtalk.reply.repository.ReplyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class ReplyService {

  private final ReplyRepository replyRepository;
  private final CommentRepository commentRepository;
  private final MemberRepository memberRepository;

  // 답댓글 쓰기
  public ReplyDto writeReply(WriteReplyRequest request) {
    Comment comment = commentRepository.findById(request.getCommentId())
        .orElseThrow(() -> new IllegalStateException("찾을 수 없는 댓글 번호입니다."));

    String email = SecurityUtil.getLoginMemberEmail();
    Member member = memberRepository.findByEmail(email)
        .orElseThrow(() -> new IllegalStateException("찾을 수 없는 멤버 이메일입니다."));

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

  @Transactional
  public ReplyDto updateReply(Long id, UpdateReplyRequest request) {
    Reply reply = replyRepository.findById(id)
        .orElseThrow(() -> new IllegalStateException("찾을 수 없는 대댓글 번호입니다."));

    String email = SecurityUtil.getLoginMemberEmail();
    Member member = memberRepository.findByEmail(email)
        .orElseThrow(() -> new IllegalStateException("찾을 수 없는 멤버 이메일입니다."));

    if (reply.getMember() != member) {
      throw new IllegalStateException("본인이 작성한 대댓글이 아닙니다.");
    }

    reply.update(request.getContent());

    return ReplyDto.fromEntity(reply);
  }
}
