package com.suhwan.cowtalk.coin.model;

import com.suhwan.cowtalk.coin.entity.Coin;
import com.suhwan.cowtalk.exchange.entity.Exchange;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
public class CoinDto {

  private Long id;
  private String initial;
  private String name;
  private Exchange exchange;
  private LocalDateTime createDateTime;
  private LocalDateTime updateDateTime;
  private LocalDateTime deleteDateTime;

  public static CoinDto fromEntity(Coin coin) {
    return CoinDto.builder()
        .id(coin.getId())
        .initial(coin.getInitial())
        .name(coin.getName())
        .exchange(coin.getExchange())
        .createDateTime(coin.getCreateDateTime())
        .updateDateTime(coin.getUpdateDateTime())
        .deleteDateTime(coin.getDeleteDateTime())
        .build();
  }
}
