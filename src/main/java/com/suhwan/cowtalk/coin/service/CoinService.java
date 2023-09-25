package com.suhwan.cowtalk.coin.service;

import com.suhwan.cowtalk.coin.entity.Coin;
import com.suhwan.cowtalk.coin.model.CoinDto;
import com.suhwan.cowtalk.coin.repository.CoinRepository;
import com.suhwan.cowtalk.exchange.entity.Exchange;
import com.suhwan.cowtalk.exchange.repository.ExchangeRepository;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class CoinService {

  private final CoinRepository coinRepository;
  private final ExchangeRepository exchangeRepository;

  // 특정 코인 조회
  @Transactional(readOnly = true)
  public CoinDto getCoin(Long id) {
    Coin coin = coinRepository.findById(id)
        .orElseThrow(() -> new IllegalStateException("찾을 수 없는 코인 번호입니다."));

    return CoinDto.fromEntity(coin);
  }

  // 거래소에 상장된 코인 조회
  public List<CoinDto> getCoinList(Long exchangeId, int page, int size) {
    Exchange exchange = exchangeRepository.findById(exchangeId)
        .orElseThrow(() -> new IllegalStateException("찾을 수 없는 거래소 번호입니다."));

    Pageable pageable = PageRequest.of(page, size);
    Page<Coin> coinPage = coinRepository.findAllByExchange(exchange, pageable);

    return coinPage.stream()
        .map(CoinDto::fromEntity)
        .collect(Collectors.toList());
  }

  // 코인 삭제
  @Transactional
  public CoinDto deleteCoin(Long id) {
    Coin coin = coinRepository.findById(id)
        .orElseThrow(() -> new IllegalStateException("찾을 수 없는 코인 번호입니다."));

    if (coin.getDeleteDateTime() != null) {
      throw new IllegalStateException("이미 삭제된 코인 번호입니다.");
    }

    coin.delete();

    return CoinDto.fromEntity(coin);
  }
}
