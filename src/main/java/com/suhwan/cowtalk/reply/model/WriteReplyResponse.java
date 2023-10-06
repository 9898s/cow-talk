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
public class WriteReplyResponse {

  private Long id;
  private String content;
  private Long commentId;
  private String nickname;

  @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
  private LocalDateTime createDateTime;

  public static WriteReplyResponse from(ReplyDto replyDto) {

    return WriteReplyResponse.builder()
        .id(replyDto.getId())
        .content(replyDto.getContent())
        .commentId(replyDto.getComment().getId())
        .nickname(replyDto.getMember().getNickname())
        .createDateTime(replyDto.getCreateDateTime())
        .build();
  }
}
