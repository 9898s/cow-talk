package com.suhwan.cowtalk.reply.model;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
public class ReplyResponseList {

  private int totalCount;
  private List<ReplyResponse> replyResponseList;

  public static ReplyResponseList of(int totalCount, List<ReplyResponse> replyResponseList) {

    return ReplyResponseList.builder()
        .totalCount(totalCount)
        .replyResponseList(replyResponseList)
        .build();
  }
}
