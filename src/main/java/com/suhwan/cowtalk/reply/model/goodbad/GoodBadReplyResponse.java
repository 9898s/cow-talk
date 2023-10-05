package com.suhwan.cowtalk.reply.model.goodbad;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.suhwan.cowtalk.common.type.GoodBad;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
public class GoodBadReplyResponse {

  private Long id;
  private Long replyId;
  private Long memberId;
  private GoodBad goodBad;

  @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
  private LocalDateTime createDateTime;

  public static GoodBadReplyResponse from(ReplyGoodBadDto replyGoodBadDto) {

    return GoodBadReplyResponse.builder()
        .id(replyGoodBadDto.getId())
        .replyId(replyGoodBadDto.getReply().getId())
        .memberId(replyGoodBadDto.getMember().getId())
        .goodBad(replyGoodBadDto.getGoodBad())
        .createDateTime(replyGoodBadDto.getCreateDateTime())
        .build();
  }
}
