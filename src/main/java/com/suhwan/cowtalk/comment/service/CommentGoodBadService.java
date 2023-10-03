package com.suhwan.cowtalk.comment.service;

import com.suhwan.cowtalk.comment.entity.Comment;
import com.suhwan.cowtalk.comment.entity.CommentGoodBad;
import com.suhwan.cowtalk.comment.model.goodbad.CommentGoodBadCache;
import com.suhwan.cowtalk.comment.model.goodbad.CommentGoodBadDto;
import com.suhwan.cowtalk.comment.repository.CommentGoodBadCacheRepository;
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
  private final CommentGoodBadCacheRepository commentGoodBadCacheRepository;
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

    String commentGoodBadId = id + ":" + member.getId();
    if (commentGoodBadCacheRepository.existsById(commentGoodBadId)) {
      throw new IllegalStateException("이미 좋아요 또는 싫어요를 누르셨습니다..");
    } else {
      if (commentGoodBadRepository.existsByCommentAndMember(comment, member)) {
        throw new IllegalStateException("이미 좋아요 또는 싫어요를 누르셨습니다.");
      }

      // 블라인드 처리
      if (!comment.isBlind() &&
          commentGoodBadRepository.countByCommentAndGoodBad(comment, GoodBad.BAD) >= BLIND_COUNT) {
        comment.blind();
      }

      // 캐시 저장
      commentGoodBadCacheRepository.save(
          CommentGoodBadCache.builder()
              .id(commentGoodBadId)
              .commentId(id)
              .memberId(member.getId())
              .build()
      );

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

  public Long countGoodBad(Long id, GoodBad goodBad) {
    Comment comment = commentRepository.findById(id)
        .orElseThrow(() -> new IllegalStateException("찾을 수 없는 댓글 번호입니다."));

    return commentGoodBadRepository.countByCommentAndGoodBad(comment, goodBad);
  }
}
