package com.suhwan.cowtalk.post.batch.scheduler;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.JobParametersInvalidException;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.repository.JobExecutionAlreadyRunningException;
import org.springframework.batch.core.repository.JobInstanceAlreadyCompleteException;
import org.springframework.batch.core.repository.JobRestartException;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Slf4j
@RequiredArgsConstructor
@Component
public class PostBatchScheduler {

  private final JobLauncher jobLauncher;

  private final Job syncElasticSearchJob;

  @Scheduled(fixedRate = 1000 * 60)
  public void runPostBatchJob()
      throws JobInstanceAlreadyCompleteException, JobExecutionAlreadyRunningException, JobParametersInvalidException, JobRestartException {
    JobParameters params = new JobParametersBuilder()
        .addString("JobId", String.valueOf(System.currentTimeMillis()))
        .toJobParameters();

    jobLauncher.run(syncElasticSearchJob, params);
  }
}
