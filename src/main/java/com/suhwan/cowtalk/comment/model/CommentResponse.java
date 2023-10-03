package com.suhwan.cowtalk.comment.model;

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
public class CommentResponse {

  private Long id;
  private String content;
  private Boolean isBlind;
  private Long postId;
  private String nickname;

  @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
  private LocalDateTime createDateTime;

  @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
  private LocalDateTime updateDateTime;

  @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
  private LocalDateTime deleteDateTime;

  public static CommentResponse from(CommentDto commentDto) {

    return CommentResponse.builder()
        .id(commentDto.getId())
        .content(commentDto.getContent())
        .isBlind(commentDto.isBlind())
        .postId(commentDto.getPost().getId())
        .nickname(commentDto.getMember().getNickname())
        .createDateTime(commentDto.getCreateDateTime())
        .updateDateTime(commentDto.getUpdateDateTime())
        .deleteDateTime(commentDto.getDeleteDateTime())
        .build();
  }
}
