package com.suhwan.cowtalk.exchange.controller;

import com.suhwan.cowtalk.exchange.entity.Exchange;
import com.suhwan.cowtalk.exchange.model.ExchangeAddRequest;
import com.suhwan.cowtalk.exchange.model.ExchangeAddResponse;
import com.suhwan.cowtalk.exchange.model.ExchangeDeleteResponse;
import com.suhwan.cowtalk.exchange.model.ExchangeEditRequest;
import com.suhwan.cowtalk.exchange.model.ExchangeEditResponse;
import com.suhwan.cowtalk.exchange.model.ExchangeInfoResponse;
import com.suhwan.cowtalk.exchange.service.ExchangeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RequestMapping("/api/exchange")
@RestController
public class ExchangeApiController {

  private final ExchangeService exchangeService;

  @PostMapping("/add")
  public ResponseEntity<?> addExchange(@RequestBody ExchangeAddRequest request) {
    ExchangeAddResponse response = exchangeService.insertExchange(request);
    return ResponseEntity.ok().body(response);
  }

  @GetMapping("/{id}")
  public ResponseEntity<?> infoExchange(@PathVariable Long id) {
    Exchange exchange = exchangeService.readExchange(id);
    return ResponseEntity.ok().body(ExchangeInfoResponse.of(exchange));
  }

  @PutMapping("/edit")
  public ResponseEntity<?> editExchange(@RequestBody ExchangeEditRequest request) {
    Exchange exchange = exchangeService.updateExchange(request);
    return ResponseEntity.ok().body(ExchangeEditResponse.of(exchange));
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<?> removeExchange(@PathVariable Long id) {
    Exchange exchange = exchangeService.deleteExchange(id);
    return ResponseEntity.ok().body(ExchangeDeleteResponse.of(exchange));
  }
}
