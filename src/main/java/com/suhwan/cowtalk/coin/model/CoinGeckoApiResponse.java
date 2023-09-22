package com.suhwan.cowtalk.coin.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class CoinGeckoApiResponse {

  @JsonProperty("tickers")
  private List<CoinGeckoApiTicker> coinGeckoApiTickerList;
}
