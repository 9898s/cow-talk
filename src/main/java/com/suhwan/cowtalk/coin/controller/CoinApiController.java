package com.suhwan.cowtalk.coin.controller;

import com.suhwan.cowtalk.coin.model.CoinDto;
import com.suhwan.cowtalk.coin.model.DeleteCoinResponse;
import com.suhwan.cowtalk.coin.service.CoinService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RequestMapping("/api/coin")
@RestController
public class CoinApiController {

  private final CoinService coinService;

  @DeleteMapping("/{id}")
  public ResponseEntity<?> deleteCoin(@PathVariable Long id) {
    CoinDto coinDto = coinService.deleteCoin(id);
    return ResponseEntity.ok().body(DeleteCoinResponse.from(coinDto));
  }
}
