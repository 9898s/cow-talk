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
public class AddExchangeResponse {

  private Long id;
  private String koreanName;
  private String englishName;

  @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
  private LocalDateTime createDate;

  public static AddExchangeResponse from(ExchangeDto exchangeDto) {
    return AddExchangeResponse.builder()
        .id(exchangeDto.getId())
        .koreanName(exchangeDto.getKoreanName())
        .englishName(exchangeDto.getEnglishName())
        .createDate(exchangeDto.getCreateDate())
        .build();
  }
}
