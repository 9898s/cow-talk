package com.suhwan.cowtalk.exchange.controller;

import com.suhwan.cowtalk.exchange.model.AddExchangeRequest;
import com.suhwan.cowtalk.exchange.model.AddExchangeResponse;
import com.suhwan.cowtalk.exchange.model.DeleteExchangeResponse;
import com.suhwan.cowtalk.exchange.model.ExchangeResponse;
import com.suhwan.cowtalk.exchange.model.UpdateExchangeRequest;
import com.suhwan.cowtalk.exchange.model.UpdateExchangeResponse;
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

  @PostMapping
  public ResponseEntity<?> createExchange(@RequestBody AddExchangeRequest request) {

    return ResponseEntity.ok()
        .body(AddExchangeResponse.from(exchangeService.createExchange(request)));
  }

  @GetMapping("/{id}")
  public ResponseEntity<?> getExchange(@PathVariable Long id) {

    return ResponseEntity.ok()
        .body(ExchangeResponse.from(exchangeService.getExchange(id)));
  }

  @PutMapping("/{id}")
  public ResponseEntity<?> updateExchange(@PathVariable Long id,
      @RequestBody UpdateExchangeRequest request) {

    return ResponseEntity.ok()
        .body(UpdateExchangeResponse.from(exchangeService.updateExchange(id, request)));
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<?> deleteExchange(@PathVariable Long id) {

    return ResponseEntity.ok()
        .body(DeleteExchangeResponse.from(exchangeService.deleteExchange(id)));
  }
}
