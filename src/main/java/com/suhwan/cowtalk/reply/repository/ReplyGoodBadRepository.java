package com.suhwan.cowtalk.reply.repository;

import com.suhwan.cowtalk.common.type.GoodBad;
import com.suhwan.cowtalk.member.entity.Member;
import com.suhwan.cowtalk.reply.entity.Reply;
import com.suhwan.cowtalk.reply.entity.ReplyGoodBad;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReplyGoodBadRepository extends JpaRepository<ReplyGoodBad, Long> {

  boolean existsByReplyAndMember(Reply reply, Member member);

  long countByReplyAndGoodBad(Reply reply, GoodBad goodBad);
}
