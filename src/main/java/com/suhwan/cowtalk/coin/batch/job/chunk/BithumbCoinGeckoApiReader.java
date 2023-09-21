package com.suhwan.cowtalk.coin.batch.job.chunk;

import com.suhwan.cowtalk.coin.model.CoinDto;
import com.suhwan.cowtalk.coin.service.CoinGeckoApiService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.item.ItemReader;

@Slf4j
@RequiredArgsConstructor
public class BithumbCoinGeckoApiReader implements ItemReader<List<CoinDto>> {

  private final CoinGeckoApiService coinGeckoApiService;

  private static final int MAX_PAGES = 5;

  private int page = 0;

  @Override
  public List<CoinDto> read() throws Exception {
    page++;

    if (page >= MAX_PAGES) {
      return null;
    }

    String exchangeName = "bithumb";
    log.info("{} coin fetch page = {}", exchangeName, page);
    List<CoinDto> coinDtoList = coinGeckoApiService.fetchCoinList(exchangeName, page);
    log.info("{} coin fetch data size =  {}", exchangeName, coinDtoList.size());
    return coinDtoList;
  }
}
