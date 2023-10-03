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
public class WriteCommentResponse {

  private Long id;
  private String content;
  private Long postId;
  private String nickname;

  @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
  private LocalDateTime createDateTime;

  public static WriteCommentResponse from(CommentDto commentDto) {

    return WriteCommentResponse.builder()
        .id(commentDto.getId())
        .content(commentDto.getContent())
        .postId(commentDto.getPost().getId())
        .nickname(commentDto.getMember().getNickname())
        .createDateTime(commentDto.getCreateDateTime())
        .build();
  }
}
