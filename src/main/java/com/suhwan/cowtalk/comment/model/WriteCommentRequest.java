package com.suhwan.cowtalk.comment.model;

import lombok.Getter;

@Getter
public class WriteCommentRequest {

  private Long postId;
  private String content;
}
