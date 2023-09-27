package com.suhwan.cowtalk.post.model;

import lombok.Getter;

@Getter
public class WritePostRequest {

  private Long categoryId;
  private String title;
  private String content;
}
