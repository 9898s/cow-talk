package com.suhwan.cowtalk.reply.model;

import lombok.Getter;

@Getter
public class WriteReplyRequest {

  private Long commentId;
  private String content;
}
