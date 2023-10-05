package com.suhwan.cowtalk.comment.service;

import static com.suhwan.cowtalk.common.type.ErrorCode.ALREADY_GOOD_BAD_COMMENT;
import static com.suhwan.cowtalk.common.type.ErrorCode.CANNOT_GOOD_BAD_OWN_COMMENT;
import static com.suhwan.cowtalk.common.type.ErrorCode.INVALID_COMMENT_ID;
import static com.suhwan.cowtalk.common.type.ErrorCode.INVALID_MEMBER_EMAIL;

import com.suhwan.cowtalk.comment.entity.Comment;
import com.suhwan.cowtalk.comment.entity.CommentGoodBad;
import com.suhwan.cowtalk.comment.model.goodbad.CommentGoodBadCache;
import com.suhwan.cowtalk.comment.model.goodbad.CommentGoodBadDto;
import com.suhwan.cowtalk.comment.repository.CommentGoodBadCacheRepository;
import com.suhwan.cowtalk.comment.repository.CommentGoodBadRepository;
import com.suhwan.cowtalk.comment.repository.CommentRepository;
import com.suhwan.cowtalk.common.exception.CommentException;
import com.suhwan.cowtalk.common.exception.MemberException;
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
        .orElseThrow(() -> new CommentException(INVALID_COMMENT_ID));

    String email = SecurityUtil.getLoginMemberEmail();
    Member member = memberRepository.findByEmail(email)
        .orElseThrow(() -> new MemberException(INVALID_MEMBER_EMAIL));

    if (comment.getMember() == member) {
      throw new CommentException(CANNOT_GOOD_BAD_OWN_COMMENT);
    }

    String commentGoodBadId = getString(id, comment, member);

    // 블라인드 처리
    if (!comment.isBlind() &&
        commentGoodBadRepository.countByCommentAndGoodBad(comment, GoodBad.BAD) >= BLIND_COUNT) {
      comment.blind();
    }

    // redis에 저장
    saveCache(id, member, commentGoodBadId);

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

  private String getString(Long id, Comment comment, Member member) {
    String commentGoodBadId = id + ":" + member.getId();
    if (commentGoodBadCacheRepository.existsById(commentGoodBadId)) {
      throw new CommentException(ALREADY_GOOD_BAD_COMMENT);
    } else {
      if (commentGoodBadRepository.existsByCommentAndMember(comment, member)) {
        // redis에 저장
        saveCache(id, member, commentGoodBadId);

        throw new CommentException(ALREADY_GOOD_BAD_COMMENT);
      }
    }
    return commentGoodBadId;
  }

  private void saveCache(Long id, Member member, String commentGoodBadId) {
    // 캐시 저장
    commentGoodBadCacheRepository.save(
        CommentGoodBadCache.builder()
            .id(commentGoodBadId)
            .commentId(id)
            .memberId(member.getId())
            .build()
    );
  }

  public Long countGoodBad(Long id, GoodBad goodBad) {
    Comment comment = commentRepository.findById(id)
        .orElseThrow(() -> new CommentException(INVALID_COMMENT_ID));

    return commentGoodBadRepository.countByCommentAndGoodBad(comment, goodBad);
  }
}
