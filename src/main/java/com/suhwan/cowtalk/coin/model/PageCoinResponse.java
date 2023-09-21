package com.suhwan.cowtalk.coin.model;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
public class PageCoinResponse {

  private int totalCount;
  private int currentPage;
  private int size;
  private List<CoinResponse> coinResponseList;

  public static PageCoinResponse of(int totalCount, int currentPage, int size, List<CoinResponse> coinResponseList) {

    return PageCoinResponse.builder()
        .totalCount(totalCount)
        .currentPage(currentPage)
        .size(size)
        .coinResponseList(coinResponseList)
        .build();
  }
}
