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
public class CoinResponseList {

  private int totalCount;
  private List<CoinResponse> coinResponseList;

  public static CoinResponseList of(int totalCount, List<CoinResponse> coinResponseList) {

    return CoinResponseList.builder()
        .totalCount(totalCount)
        .coinResponseList(coinResponseList)
        .build();
  }
}
