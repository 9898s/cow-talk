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
public class DeleteCommentResponse {

  private Long id;
  private String content;
  private String nickname;

  @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
  private LocalDateTime deleteDateTime;

  public static DeleteCommentResponse from(CommentDto commentDto) {

    return DeleteCommentResponse.builder()
        .id(commentDto.getId())
        .content(commentDto.getContent())
        .nickname(commentDto.getMember().getNickname())
        .deleteDateTime(commentDto.getDeleteDateTime())
        .build();
  }
}
