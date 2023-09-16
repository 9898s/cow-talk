package com.suhwan.cowtalk.exchange.controller;

import com.suhwan.cowtalk.exchange.entity.Exchange;
import com.suhwan.cowtalk.exchange.model.ExchangeResponse;
import com.suhwan.cowtalk.exchange.service.ExchangeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RequestMapping("/api/exchange")
@RestController
public class ExchangeApiController {

    private final ExchangeService exchangeService;

    @PostMapping("/{name}")
    public ResponseEntity<?> addExchange(@PathVariable String name) {
        Exchange exchange = exchangeService.insertExchange(name);
        return ResponseEntity.ok().body(ExchangeResponse.of(exchange));
    }
}
