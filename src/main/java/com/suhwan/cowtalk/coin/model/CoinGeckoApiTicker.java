package com.suhwan.cowtalk.coin.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class CoinGeckoApiTicker {

  @JsonProperty("base")
  private String initial;

  @JsonProperty("target")
  private String target;

  @JsonProperty("coin_id")
  private String name;
}
