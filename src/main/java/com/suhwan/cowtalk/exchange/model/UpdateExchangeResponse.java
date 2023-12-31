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
public class UpdateExchangeResponse {

  private Long id;
  private String koreanName;
  private String englishName;

  @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
  private LocalDateTime updateDateTime;

  public static UpdateExchangeResponse from(ExchangeDto exchange) {
    return UpdateExchangeResponse.builder()
        .id(exchange.getId())
        .koreanName(exchange.getKoreanName())
        .englishName(exchange.getEnglishName())
        .updateDateTime(exchange.getUpdateDateTime())
        .build();
  }
}
