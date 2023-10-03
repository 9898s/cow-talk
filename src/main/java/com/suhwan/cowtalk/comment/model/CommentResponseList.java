package com.suhwan.cowtalk.comment.model;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
public class CommentResponseList {

  private int totalCount;
  private List<CommentResponse> commentResponseList;

  public static CommentResponseList of(int totalCount, List<CommentResponse> commentResponseList) {

    return CommentResponseList.builder()
        .totalCount(totalCount)
        .commentResponseList(commentResponseList)
        .build();
  }
}
