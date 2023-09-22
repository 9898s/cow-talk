package com.suhwan.cowtalk.coin.batch.scheduler;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecutionException;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Slf4j
@RequiredArgsConstructor
@Component
public class CoinBatchScheduler {

  private final JobLauncher jobLauncher;

  private final Job fetchUpbitCoinJob;
  private final Job fetchBithumbCoinJob;

  @Scheduled(fixedRate = 1000 * 60, initialDelay = 1000 * 60)
//  @Scheduled(cron = "0 0 * * * *")
  public void executeFetchUpbitCoinJob() {
    try {
      log.info("fetchUpbitCoinJob start");

      JobParameters params = new JobParametersBuilder()
          .addString("JobID", String.valueOf(System.currentTimeMillis()))
          .toJobParameters();

      jobLauncher.run(fetchUpbitCoinJob, params);

      log.info("successfully complete fetchUpbitCoinJob");
    } catch (JobExecutionException e) {
      e.printStackTrace();
    }
  }

  @Scheduled(fixedRate = 1000 * 60, initialDelay = 1000 * 120)
//  @Scheduled(cron = "0 30 * * * *")
  public void executeFetchBithumbCoinJob() {
    try {
      log.info("fetchBithumbCoinJob start");

      JobParameters params = new JobParametersBuilder()
          .addString("JobID", String.valueOf(System.currentTimeMillis()))
          .toJobParameters();

      jobLauncher.run(fetchBithumbCoinJob, params);

      log.info("successfully complete fetchBithumbCoinJob");
    } catch (JobExecutionException e) {
      e.printStackTrace();
    }
  }
}
