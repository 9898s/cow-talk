package com.suhwan.cowtalk.coin.controller;

import com.suhwan.cowtalk.coin.model.CoinDto;
import com.suhwan.cowtalk.coin.model.CoinResponse;
import com.suhwan.cowtalk.coin.model.CoinResponseList;
import com.suhwan.cowtalk.coin.model.DeleteCoinResponse;
import com.suhwan.cowtalk.coin.service.CoinService;
import com.suhwan.cowtalk.exchange.service.ExchangeService;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RequestMapping("/api/coin")
@RestController
public class CoinApiController {

  private final CoinService coinService;
  private final ExchangeService exchangeService;

  @GetMapping("/{id}")
  public ResponseEntity<?> getCoin(@PathVariable Long id) {
    CoinDto coinDto = coinService.getCoin(id);
    return ResponseEntity.ok().body(CoinResponse.from(coinDto));
  }

  @GetMapping("/exchange/{exchangeId}")
  public ResponseEntity<?> getCoinList(@PathVariable Long exchangeId) {
    List<CoinDto> coinDtoList = coinService.getCoinList(exchangeId);

    List<CoinResponse> coinResponseList = coinDtoList.stream()
        .map(CoinResponse::from)
        .collect(Collectors.toList());

    return ResponseEntity.ok().body(CoinResponseList.of(coinResponseList.size(), coinResponseList));
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<?> deleteCoin(@PathVariable Long id) {
    CoinDto coinDto = coinService.deleteCoin(id);
    return ResponseEntity.ok().body(DeleteCoinResponse.from(coinDto));
  }
}
