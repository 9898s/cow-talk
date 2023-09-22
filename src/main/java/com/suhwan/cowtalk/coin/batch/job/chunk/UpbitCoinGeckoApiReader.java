package com.suhwan.cowtalk.coin.batch.job.chunk;

import com.suhwan.cowtalk.coin.model.CoinDto;
import com.suhwan.cowtalk.coin.service.CoinGeckoApiService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.item.ItemReader;

@Slf4j
@RequiredArgsConstructor
public class UpbitCoinGeckoApiReader implements ItemReader<List<CoinDto>> {

  private static final String EXCHANGE_NAME = "upbit";

  private final CoinGeckoApiService coinGeckoApiService;

  private int page = 0;

  @Override
  public List<CoinDto> read() throws Exception {

    List<CoinDto> coinDtoList = coinGeckoApiService.fetchCoinList(EXCHANGE_NAME, page);

    if (coinDtoList.isEmpty()) {
      return null;
    }

    log.info("{} coin fetch page = {}", EXCHANGE_NAME, page);
    log.info("{} coin fetch data size =  {}", EXCHANGE_NAME, coinDtoList.size());

    page++;
    return coinDtoList;
  }
}
