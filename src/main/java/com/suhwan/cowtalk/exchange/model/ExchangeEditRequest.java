package com.suhwan.cowtalk.exchange.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class ExchangeEditRequest {

    private Long id;
    private String name;
}
