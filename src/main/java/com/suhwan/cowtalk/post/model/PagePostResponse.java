package com.suhwan.cowtalk.post.model;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
public class PagePostResponse {

  private int totalCount;
  private int currentPage;
  private int size;
  private List<PostResponse> postResponseList;

  public static PagePostResponse of(int totalCount, int currentPage, int size,
      List<PostResponse> postResponseList) {

    return PagePostResponse.builder()
        .totalCount(totalCount)
        .currentPage(currentPage)
        .size(size)
        .postResponseList(postResponseList)
        .build();
  }
}
