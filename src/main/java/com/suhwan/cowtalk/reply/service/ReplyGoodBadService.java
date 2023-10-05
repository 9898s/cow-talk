package com.suhwan.cowtalk.reply.service;

import com.suhwan.cowtalk.common.security.SecurityUtil;
import com.suhwan.cowtalk.common.type.GoodBad;
import com.suhwan.cowtalk.member.entity.Member;
import com.suhwan.cowtalk.member.repository.MemberRepository;
import com.suhwan.cowtalk.reply.entity.Reply;
import com.suhwan.cowtalk.reply.entity.ReplyGoodBad;
import com.suhwan.cowtalk.reply.model.goodbad.ReplyGoodBadCache;
import com.suhwan.cowtalk.reply.model.goodbad.ReplyGoodBadDto;
import com.suhwan.cowtalk.reply.repository.ReplyGoodBadCacheRepository;
import com.suhwan.cowtalk.reply.repository.ReplyGoodBadRepository;
import com.suhwan.cowtalk.reply.repository.ReplyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class ReplyGoodBadService {

  private static final int BLIND_COUNT = 5;

  private final ReplyGoodBadRepository replyGoodBadRepository;
  private final ReplyGoodBadCacheRepository replyGoodBadCacheRepository;
  private final ReplyRepository replyRepository;
  private final MemberRepository memberRepository;

  // 댓글 좋아요/싫어요
  @Transactional
  public ReplyGoodBadDto goodBadReply(Long id, GoodBad goodBad) {
    Reply reply = replyRepository.findById(id)
        .orElseThrow(() -> new IllegalStateException("찾을 수 없는 대댓글 번호입니다."));

    String email = SecurityUtil.getLoginMemberEmail();
    Member member = memberRepository.findByEmail(email)
        .orElseThrow(() -> new IllegalStateException("찾을 수 없는 멤버 이메일입니다."));

//    if (reply.getMember() == member) {
//      throw new IllegalStateException("자신이 작성한 대댓글에 좋아요/싫어요를 누를 수 없습니다.");
//    }

    String replyGoodBadId = getString(id, reply, member);

    // 블라인드 처리
    if (!reply.isBlind() &&
        replyGoodBadRepository.countByReplyAndGoodBad(reply, GoodBad.BAD) >= BLIND_COUNT) {
      reply.blind();
    }

    // redis에 저장
    saveCache(id, member, replyGoodBadId);

    return ReplyGoodBadDto.fromEntity(
        replyGoodBadRepository.save(
            ReplyGoodBad.builder()
                .reply(reply)
                .member(member)
                .goodBad(goodBad)
                .build()
        )
    );
  }

  private String getString(Long id, Reply reply, Member member) {
    String replyGoodBadId = id + ":" + member.getId();
    if (replyGoodBadCacheRepository.existsById(replyGoodBadId)) {
      throw new IllegalStateException("이미 좋아요 또는 싫어요를 누르셨습니다.");
    } else {
      if (replyGoodBadRepository.existsByReplyAndMember(reply, member)) {
        // redis에 저장
        saveCache(id, member, replyGoodBadId);

        throw new IllegalStateException("이미 좋아요 또는 싫어요를 누르셨습니다.");
      }
    }
    return replyGoodBadId;
  }

  private void saveCache(Long id, Member member, String replyGoodBadId) {
    // 캐시 저장
    replyGoodBadCacheRepository.save(
        ReplyGoodBadCache.builder()
            .id(replyGoodBadId)
            .replyId(id)
            .memberId(member.getId())
            .build()
    );
  }
}
