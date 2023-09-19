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
  private String name;

  @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
  private LocalDateTime createDate;

  public static AddExchangeResponse from(ExchangeDto exchangeDto) {
    return AddExchangeResponse.builder()
        .id(exchangeDto.getId())
        .name(exchangeDto.getName())
        .createDate(exchangeDto.getCreateDate())
        .build();
  }
}
