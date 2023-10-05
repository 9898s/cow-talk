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
public class UpdateCommentResponse {

  private Long id;
  private String content;
  private String nickname;

  @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
  private LocalDateTime updateDateTime;

  public static UpdateCommentResponse from(CommentDto commentDto) {

    return UpdateCommentResponse.builder()
        .id(commentDto.getId())
        .content(commentDto.getContent())
        .nickname(commentDto.getMember().getNickname())
        .updateDateTime(commentDto.getUpdateDateTime())
        .build();
  }
}
