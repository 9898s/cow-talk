package com.suhwan.cowtalk.exchange.model;

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
public class ExchangeDto {

  private Long id;
  private String koreanName;
  private String englishName;
  private LocalDateTime createDate;
  private LocalDateTime updateDate;
  private LocalDateTime deleteDate;

  public static ExchangeDto fromEntity(Exchange exchange) {
    return ExchangeDto.builder()
        .id(exchange.getId())
        .koreanName(exchange.getKoreanName())
        .englishName(exchange.getEnglishName())
        .createDate(exchange.getCreateDate())
        .updateDate(exchange.getUpdateDate())
        .deleteDate(exchange.getDeleteDate())
        .build();
  }
}
