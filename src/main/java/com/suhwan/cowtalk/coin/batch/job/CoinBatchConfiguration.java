package com.suhwan.cowtalk.coin.batch.job;

import com.suhwan.cowtalk.coin.batch.job.chunk.BithumbCoinGeckoApiReader;
import com.suhwan.cowtalk.coin.batch.job.chunk.CoinGeckoApiWriter;
import com.suhwan.cowtalk.coin.batch.job.chunk.UpbitCoinGeckoApiReader;
import com.suhwan.cowtalk.coin.model.CoinDto;
import com.suhwan.cowtalk.coin.service.CoinGeckoApiService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.JobScope;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class CoinBatchConfiguration {

  private final JobBuilderFactory jobBuilderFactory;
  private final StepBuilderFactory stepBuilderFactory;
  private final CoinGeckoApiService coinGeckoApiService;

  @Bean
  public Job fetchUpbitCoinJob() {
    return jobBuilderFactory.get("fetchUpbitCoinJob")
        .start(fetchUpbitCoinStep())
        .build();
  }

  @Bean
  public Job fetchBithumbCoinJob() {
    return jobBuilderFactory.get("fetchBithumbCoinJob")
        .start(fetchBithumbCoinStep())
        .build();
  }

  @Bean
  @JobScope
  public Step fetchUpbitCoinStep() {
    return stepBuilderFactory.get("fetchUpbitCoinStep")
        .<List<CoinDto>, List<CoinDto>>chunk(10)
        .reader(upbitCoinGeckoApiReader())
        .writer(coinGeckoApiWriter())
        .build();
  }

  @Bean
  @JobScope
  public Step fetchBithumbCoinStep() {
    return stepBuilderFactory.get("fetchBithumbCoinStep")
        .<List<CoinDto>, List<CoinDto>>chunk(10)
        .reader(bithumbCoinGeckoApiReader())
        .writer(coinGeckoApiWriter())
        .build();
  }

  @Bean
  @StepScope
  public ItemReader<List<CoinDto>> upbitCoinGeckoApiReader() {
    return new UpbitCoinGeckoApiReader(coinGeckoApiService);
  }

  @Bean
  @StepScope
  public ItemReader<List<CoinDto>> bithumbCoinGeckoApiReader() {
    return new BithumbCoinGeckoApiReader(coinGeckoApiService);
  }

  @Bean
  @StepScope
  public ItemWriter<List<CoinDto>> coinGeckoApiWriter() {
    return new CoinGeckoApiWriter(coinGeckoApiService);
  }
}