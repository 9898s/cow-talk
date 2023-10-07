package com.suhwan.cowtalk.post.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Builder
public class SearchPostResponse {

  private Long id;
  private String title;
  private String content;
  private long view;
  private Boolean isBlind;
  private String nickname;

  public static SearchPostResponse from(PostDto postDto) {

    return SearchPostResponse.builder()
        .id(postDto.getId())
        .title(postDto.getTitle())
        .content(postDto.getContent())
        .view(postDto.getView())
        .isBlind(postDto.getIsBlind())
        .nickname(postDto.getMember().getNickname())
        .build();
  }
}
