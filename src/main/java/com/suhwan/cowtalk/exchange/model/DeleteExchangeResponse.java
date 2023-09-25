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
public class DeleteExchangeResponse {

  private Long id;
  private String koreanName;
  private String englishName;

  @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
  private LocalDateTime deleteDateTime;

  public static DeleteExchangeResponse from(ExchangeDto exchangeDto) {
    return DeleteExchangeResponse.builder()
        .id(exchangeDto.getId())
        .koreanName(exchangeDto.getKoreanName())
        .englishName(exchangeDto.getEnglishName())
        .deleteDateTime(exchangeDto.getDeleteDateTime())
        .build();
  }
}
