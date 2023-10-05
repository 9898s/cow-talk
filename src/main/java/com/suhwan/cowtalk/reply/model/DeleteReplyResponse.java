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
public class DeleteReplyResponse {

  private Long id;
  private String content;
  private String nickname;

  @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
  private LocalDateTime deleteDateTime;

  public static DeleteReplyResponse from(ReplyDto replyDto) {

    return DeleteReplyResponse.builder()
        .id(replyDto.getId())
        .content(replyDto.getContent())
        .nickname(replyDto.getMember().getNickname())
        .deleteDateTime(replyDto.getDeleteDateTime())
        .build();
  }
}
