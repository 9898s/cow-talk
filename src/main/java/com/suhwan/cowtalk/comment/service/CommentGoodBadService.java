package com.suhwan.cowtalk.comment.service;

import com.suhwan.cowtalk.comment.entity.Comment;
import com.suhwan.cowtalk.comment.entity.CommentGoodBad;
import com.suhwan.cowtalk.comment.model.goodbad.CommentGoodBadDto;
import com.suhwan.cowtalk.comment.repository.CommentGoodBadRepository;
import com.suhwan.cowtalk.comment.repository.CommentRepository;
import com.suhwan.cowtalk.common.security.SecurityUtil;
import com.suhwan.cowtalk.common.type.GoodBad;
import com.suhwan.cowtalk.member.entity.Member;
import com.suhwan.cowtalk.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class CommentGoodBadService {

  private static final int BLIND_COUNT = 5;

  private final CommentGoodBadRepository commentGoodBadRepository;
  private final CommentRepository commentRepository;
  private final MemberRepository memberRepository;

  // 댓글 좋아요/싫어요
  @Transactional
  public CommentGoodBadDto goodBadComment(Long id, GoodBad goodBad) {
    Comment comment = commentRepository.findById(id)
        .orElseThrow(() -> new IllegalStateException("찾을 수 없는 댓글 번호입니다."));

    String email = SecurityUtil.getLoginMemberEmail();
    Member member = memberRepository.findByEmail(email)
        .orElseThrow(() -> new IllegalStateException("찾을 수 없는 멤버 이메일입니다."));

    if (comment.getMember() == member) {
      throw new IllegalStateException("자신이 작성한 댓글에 좋아요/싫어요를 누를 수 없습니다.");
    }

    if (commentGoodBadRepository.existsByCommentAndMember(comment, member)) {
      throw new IllegalStateException("이미 좋아요 또는 싫어요를 누르셨습니다.");
    }

    // 블라인드 처리
    if (!comment.isBlind() &&
        commentGoodBadRepository.countByCommentAndGoodBad(comment, GoodBad.BAD) >= BLIND_COUNT) {
      comment.blind();
    }

    return CommentGoodBadDto.fromEntity(
        commentGoodBadRepository.save(
            CommentGoodBad.builder()
                .comment(comment)
                .member(member)
                .goodBad(goodBad)
                .build()
        )
    );
  }
}
