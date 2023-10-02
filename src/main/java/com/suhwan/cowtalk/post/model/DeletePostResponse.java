package com.suhwan.cowtalk.post.model;

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
public class DeletePostResponse {

  private Long id;
  private String title;
  private String content;
  private String nickname;

  @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
  private LocalDateTime deleteDateTime;

  public static DeletePostResponse from(PostDto postDto) {

    return DeletePostResponse.builder()
        .id(postDto.getId())
        .title(postDto.getTitle())
        .content(postDto.getContent())
        .nickname(postDto.getMember().getNickname())
        .deleteDateTime(postDto.getCreateDateTime())
        .build();
  }
}
