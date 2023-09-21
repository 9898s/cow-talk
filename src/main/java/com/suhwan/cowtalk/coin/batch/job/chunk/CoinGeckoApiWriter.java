package com.suhwan.cowtalk.coin.batch.job.chunk;

import com.suhwan.cowtalk.coin.model.CoinDto;
import com.suhwan.cowtalk.coin.service.CoinGeckoApiService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.item.ItemWriter;

@Slf4j
@RequiredArgsConstructor
public class CoinGeckoApiWriter implements ItemWriter<List<CoinDto>> {

  private final CoinGeckoApiService coinGeckoApiService;

  @Override
  public void write(List<? extends List<CoinDto>> items) throws Exception {
    items.forEach(coinGeckoApiService::createCoinList);
  }
}
