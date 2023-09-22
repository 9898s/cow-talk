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
public class CoinResponse {

  private Long id;
  private String initial;
  private String name;
  private String exchangeName;

  @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
  private LocalDateTime createDate;

  @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
  private LocalDateTime updateDate;

  @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
  private LocalDateTime deleteDate;

  public static CoinResponse from(CoinDto coinDto) {
    return CoinResponse.builder()
        .id(coinDto.getId())
        .initial(coinDto.getInitial())
        .name(coinDto.getName())
        .exchangeName(coinDto.getExchange().getEnglishName())
        .createDate(coinDto.getCreateDate())
        .updateDate(coinDto.getUpdateDate())
        .deleteDate(coinDto.getDeleteDate())
        .build();
  }
}
