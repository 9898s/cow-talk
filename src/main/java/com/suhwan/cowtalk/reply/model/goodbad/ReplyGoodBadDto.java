package com.suhwan.cowtalk.reply.model.goodbad;

import com.suhwan.cowtalk.common.type.GoodBad;
import com.suhwan.cowtalk.member.entity.Member;
import com.suhwan.cowtalk.reply.entity.Reply;
import com.suhwan.cowtalk.reply.entity.ReplyGoodBad;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
public class ReplyGoodBadDto {

  private Long id;
  private GoodBad goodBad;
  private Reply reply;
  private Member member;
  private LocalDateTime createDateTime;

  public static ReplyGoodBadDto fromEntity(ReplyGoodBad replyGoodBad) {

    return ReplyGoodBadDto.builder()
        .id(replyGoodBad.getId())
        .goodBad(replyGoodBad.getGoodBad())
        .reply(replyGoodBad.getReply())
        .member(replyGoodBad.getMember())
        .createDateTime(replyGoodBad.getCreateDateTime())
        .build();
  }
}
