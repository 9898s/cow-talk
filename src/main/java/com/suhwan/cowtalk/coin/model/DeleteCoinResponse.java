package com.suhwan.cowtalk.coin.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
public class DeleteCoinResponse {

  private Long id;
  private String initial;
  private String name;
  private String exchangeName;

  @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
  private LocalDateTime deleteDateTime;

  public static DeleteCoinResponse from(CoinDto coinDto) {
    return DeleteCoinResponse.builder()
        .id(coinDto.getId())
        .initial(coinDto.getInitial())
        .name(coinDto.getName())
        .exchangeName(coinDto.getExchange().getEnglishName())
        .deleteDateTime(coinDto.getDeleteDateTime())
        .build();
  }
}
