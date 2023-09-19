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
  private String name;

  @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
  private LocalDateTime deleteDate;

  public static DeleteExchangeResponse from(ExchangeDto exchangeDto) {
    return DeleteExchangeResponse.builder()
        .id(exchangeDto.getId())
        .name(exchangeDto.getName())
        .deleteDate(exchangeDto.getDeleteDate())
        .build();
  }
}
