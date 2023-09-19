package com.suhwan.cowtalk.exchange.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.suhwan.cowtalk.exchange.entity.Exchange;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
public class ExchangeAddResponse {

    private Long id;
    private String name;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createDate;

    public static ExchangeAddResponse of(Exchange exchange) {
        return ExchangeAddResponse.builder()
                .id(exchange.getId())
                .name(exchange.getName())
                .createDate(exchange.getCreateDate())
                .build();
    }
}
