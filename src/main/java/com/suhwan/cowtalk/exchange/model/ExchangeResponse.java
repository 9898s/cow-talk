package com.suhwan.cowtalk.exchange.model;

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
public class ExchangeResponse {

  private Long id;
  private String koreanName;
  private String englishName;

  @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
  private LocalDateTime createDateTime;

  @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
  private LocalDateTime updateDateTime;

  @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
  private LocalDateTime deleteDateTime;

  public static ExchangeResponse from(ExchangeDto exchangeDto) {
    return ExchangeResponse.builder()
        .id(exchangeDto.getId())
        .koreanName(exchangeDto.getKoreanName())
        .englishName(exchangeDto.getEnglishName())
        .createDateTime(exchangeDto.getCreateDateTime())
        .updateDateTime(exchangeDto.getUpdateDateTime())
        .deleteDateTime(exchangeDto.getDeleteDateTime())
        .build();
  }
}
