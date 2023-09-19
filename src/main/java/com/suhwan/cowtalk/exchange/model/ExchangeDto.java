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
  private String name;
  private LocalDateTime createDate;
  private LocalDateTime updateDate;
  private LocalDateTime deleteDate;

  public static ExchangeDto fromEntity(Exchange exchange) {
    return ExchangeDto.builder()
        .id(exchange.getId())
        .name(exchange.getName())
        .createDate(exchange.getCreateDate())
        .updateDate(exchange.getUpdateDate())
        .deleteDate(exchange.getDeleteDate())
        .build();
  }
}
