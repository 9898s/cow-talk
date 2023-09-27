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
public class WritePostResponse {

  private Long id;
  private String categoryName;
  private String title;
  private String content;
  private String nickname;

  @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
  private LocalDateTime createDateTime;

  public static WritePostResponse from(PostDto postDto) {

    return WritePostResponse.builder()
        .id(postDto.getId())
        .categoryName(postDto.getCategory().getName())
        .title(postDto.getTitle())
        .content(postDto.getContent())
        .nickname(postDto.getMember().getNickname())
        .createDateTime(postDto.getCreateDateTime())
        .build();
  }
}
