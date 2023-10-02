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
public class PostResponse {

  private Long id;
  private String title;
  private String content;
  private long view;
  private Boolean isBlind;
  private String nickname;

  @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
  private LocalDateTime createDateTime;

  @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
  private LocalDateTime updateDateTime;

  @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
  private LocalDateTime deleteDateTime;

  private Long goodCount;
  private Long badCount;

  public static PostResponse from(PostDto postDto, Long goodCount, Long badCount) {

    return PostResponse.builder()
        .id(postDto.getId())
        .title(postDto.getTitle())
        .content(postDto.getContent())
        .view(postDto.getView())
        .isBlind(postDto.getIsBlind())
        .nickname(postDto.getMember().getNickname())
        .createDateTime(postDto.getCreateDateTime())
        .updateDateTime(postDto.getUpdateDateTime())
        .deleteDateTime(postDto.getDeleteDateTime())

        .goodCount(goodCount)
        .badCount(badCount)
        .build();
  }
}
