package com.suhwan.cowtalk.reply.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
public class ReplyResponse {

  private Long id;
  private String content;
  private Boolean isBlind;
  private Long commentId;
  private String nickname;

  @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
  private LocalDateTime createDateTime;

  @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
  private LocalDateTime updateDateTime;

  @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
  private LocalDateTime deleteDateTime;

  private Long goodCount;
  private Long badCount;

  public static ReplyResponse from(ReplyDto replyDto, Long goodCount, Long badCount) {

    return ReplyResponse.builder()
        .id(replyDto.getId())
        .content(replyDto.getContent())
        .isBlind(replyDto.isBlind())
        .commentId(replyDto.getComment().getId())
        .nickname(replyDto.getMember().getNickname())
        .createDateTime(replyDto.getCreateDateTime())
        .updateDateTime(replyDto.getUpdateDateTime())
        .deleteDateTime(replyDto.getDeleteDateTime())
        .goodCount(goodCount)
        .badCount(badCount)
        .build();
  }
}
