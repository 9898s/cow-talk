package com.suhwan.cowtalk.exchange.controller;

import com.suhwan.cowtalk.exchange.entity.Exchange;
import com.suhwan.cowtalk.exchange.model.ExchangeInfoResponse;
import com.suhwan.cowtalk.exchange.model.ExchangeResponse;
import com.suhwan.cowtalk.exchange.service.ExchangeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

    @GetMapping("/{id}")
    public ResponseEntity<?> infoExchange(@PathVariable Long id) {
        Exchange exchange = exchangeService.readExchange(id);
        return ResponseEntity.ok().body(ExchangeInfoResponse.of(exchange));
    }
}
