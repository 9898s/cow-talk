package com.suhwan.cowtalk.coin.service;

import static com.suhwan.cowtalk.common.type.ErrorCode.API_CLIENT_ERROR;
import static com.suhwan.cowtalk.common.type.ErrorCode.API_SERVER_ERROR;
import static com.suhwan.cowtalk.common.type.ErrorCode.INVALID_EXCHANGE_NAME;

import com.suhwan.cowtalk.coin.entity.Coin;
import com.suhwan.cowtalk.coin.model.CoinDto;
import com.suhwan.cowtalk.coin.model.CoinGeckoApiResponse;
import com.suhwan.cowtalk.coin.model.CoinGeckoApiTicker;
import com.suhwan.cowtalk.coin.repository.CoinRepository;
import com.suhwan.cowtalk.common.exception.ApiException;
import com.suhwan.cowtalk.common.exception.ExchangeException;
import com.suhwan.cowtalk.exchange.entity.Exchange;
import com.suhwan.cowtalk.exchange.repository.ExchangeRepository;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

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
        .orElseThrow(() -> new ExchangeException(INVALID_EXCHANGE_NAME));

    String apiUrl = String.format(COIN_GECKO_API_URL, exchangeName);

    CoinGeckoApiResponse coinGeckoApiResponse = getCoinGeckoApiResponse(apiUrl, page);
    List<CoinGeckoApiTicker> coinGeckoApiTickerList = coinGeckoApiResponse.getCoinGeckoApiTickerList();

    return coinGeckoApiTickerList.stream()
        .filter(ticker -> "KRW".equals(ticker.getTarget()))
        .map(ticker -> CoinDto.builder()
            .initial(ticker.getInitial())
            .name(ticker.getName())
            .exchange(exchange)
            .build())
        .collect(Collectors.toList());
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
        .onStatus(HttpStatus::is4xxClientError, clientResponse -> {
          return Mono.error(new ApiException(API_CLIENT_ERROR));
        })
        .onStatus(HttpStatus::is5xxServerError, clientResponse -> {
          return Mono.error(new ApiException(API_SERVER_ERROR));
        })
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
            .build()
    );
  }
}
