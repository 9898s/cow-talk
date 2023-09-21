package com.suhwan.cowtalk.coin.service;

import com.suhwan.cowtalk.coin.entity.Coin;
import com.suhwan.cowtalk.coin.model.CoinDto;
import com.suhwan.cowtalk.coin.model.CoinGeckoApiResponse;
import com.suhwan.cowtalk.coin.model.CoinGeckoApiTicker;
import com.suhwan.cowtalk.coin.repository.CoinRepository;
import com.suhwan.cowtalk.exchange.entity.Exchange;
import com.suhwan.cowtalk.exchange.repository.ExchangeRepository;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.reactive.function.client.WebClient;

@Slf4j
@RequiredArgsConstructor
@Service
public class CoinGeckoApiService {

  private static final String COIN_GECKO_API_URL = "https://api.coingecko.com/api/v3/exchanges/%s/tickers";

  private final CoinRepository coinRepository;
  private final ExchangeRepository exchangeRepository;
  private final WebClient webClient;

  public List<CoinDto> fetchCoinList(String exchangeName, int page) {
    Exchange exchange = exchangeRepository.findByEnglishName(exchangeName)
        .orElseThrow(() -> new IllegalStateException("찾을 수 없는 거래소 이름입니다."));

    List<CoinDto> coinDtoList = new ArrayList<>();
    String apiUrl = String.format(COIN_GECKO_API_URL, exchangeName);

    CoinGeckoApiResponse coinGeckoApiResponse = getCoinGeckoApiResponse(apiUrl, page);
    List<CoinGeckoApiTicker> coinGeckoApiTickerList = coinGeckoApiResponse.getCoinGeckoApiTickerList();

    for (CoinGeckoApiTicker ticker : coinGeckoApiTickerList) {
      if (!ticker.getTarget().equals("KRW")) {
        continue;
      }

      coinDtoList.add(
          CoinDto.builder()
              .initial(ticker.getInitial())
              .name(ticker.getName())
              .exchange(exchange)
              .build()
      );
    }

    return coinDtoList;
  }

  private CoinGeckoApiResponse getCoinGeckoApiResponse(String apiUrl, int i) {

    return webClient.mutate()
        .baseUrl(apiUrl)
        .build()
        .get()
        .uri(uriBuilder -> uriBuilder
            .queryParam("page", i)
            .build()
        )
        .retrieve()
        .bodyToMono(CoinGeckoApiResponse.class)
        .flux()
        .toStream()
        .findFirst()
        .orElse(null);
  }

  public void createCoinList(List<CoinDto> coinDtoList) {
    for (CoinDto coinDto : coinDtoList) {
      createCoin(coinDto);
    }
  }

  @Transactional
  public void createCoin(CoinDto coinDto) {
    // 알 수 없는 거래소일 경우 패스
    if (coinDto.getExchange() == null) {
      return;
    }

    Optional<Coin> coin = coinRepository.findByInitialAndExchange(coinDto.getInitial(),
        coinDto.getExchange());

    // 이미 추가된 코인일 경우 업데이트
    if (coin.isPresent()) {
      coin.get().update();
      return;
    }

    // 아닐 경우 코인 추가
    coinRepository.save(
        Coin.builder()
            .initial(coinDto.getInitial())
            .name(coinDto.getName())
            .exchange(coinDto.getExchange())
            .createDate(LocalDateTime.now())
            .build()
    );
  }
}
