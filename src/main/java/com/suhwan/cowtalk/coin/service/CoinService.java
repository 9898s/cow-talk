package com.suhwan.cowtalk.coin.service;

import static com.suhwan.cowtalk.common.type.ErrorCode.ALREADY_DELETE_COIN_ID;
import static com.suhwan.cowtalk.common.type.ErrorCode.INVALID_COIN_ID;
import static com.suhwan.cowtalk.common.type.ErrorCode.INVALID_EXCHANGE_ID;

import com.suhwan.cowtalk.coin.entity.Coin;
import com.suhwan.cowtalk.coin.model.CoinDto;
import com.suhwan.cowtalk.coin.repository.CoinRepository;
import com.suhwan.cowtalk.common.exception.CoinException;
import com.suhwan.cowtalk.common.exception.ExchangeException;
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
        .orElseThrow(() -> new CoinException(INVALID_COIN_ID));

    return CoinDto.fromEntity(coin);
  }

  // 거래소에 상장된 코인 조회
  public List<CoinDto> getCoinList(Long exchangeId, int page, int size) {
    Exchange exchange = exchangeRepository.findById(exchangeId)
        .orElseThrow(() -> new ExchangeException(INVALID_EXCHANGE_ID));

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
        .orElseThrow(() -> new CoinException(INVALID_COIN_ID));

    if (coin.getDeleteDateTime() != null) {
      throw new CoinException(ALREADY_DELETE_COIN_ID);
    }

    coin.delete();

    return CoinDto.fromEntity(coin);
  }
}
