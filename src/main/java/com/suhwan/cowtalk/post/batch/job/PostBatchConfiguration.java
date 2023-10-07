package com.suhwan.cowtalk.post.batch.job;

import com.suhwan.cowtalk.post.document.ElasticSearchPost;
import com.suhwan.cowtalk.post.entity.Post;
import com.suhwan.cowtalk.post.repository.ElasticSearchPostRepository;
import com.suhwan.cowtalk.post.repository.PostRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.BatchStatus;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.core.listener.JobExecutionListenerSupport;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Slf4j
@Configuration
@RequiredArgsConstructor
public class PostBatchConfiguration {

  private final JobBuilderFactory jobBuilderFactory;
  private final StepBuilderFactory stepBuilderFactory;

  private final PostRepository postRepository;
  private final ElasticSearchPostRepository elasticSearchPostRepository;

  @Bean
  public Step syncElasticSearchStep() {
    return stepBuilderFactory.get("syncElasticSearchStep")
        .tasklet(syncElasticSearchTasklet())
        .build();
  }

  @Bean
  public Tasklet syncElasticSearchTasklet() {
    return (contribution, chunkContext) -> {
      List<Post> postList = postRepository.findAll();
      elasticSearchPostRepository.deleteAll();

      postList.stream()
          .map(post -> ElasticSearchPost.builder()
              .id(post.getId())
              .title(post.getTitle())
              .content(post.getContent())
              .isBlind(post.isBlind())
              .view(post.getView())
              .member(post.getMember())
              .build())
          .forEach(elasticSearchPostRepository::save);

      return RepeatStatus.FINISHED;
    };
  }

  @Bean
  public Job syncElasticSearchJob(JobCompletionNotificationListener listener) {
    return jobBuilderFactory.get("syncElasticSearchJob")
        .incrementer(new RunIdIncrementer())
        .listener(listener)
        .flow(syncElasticSearchStep())
        .end()
        .build();
  }

  @Bean
  public JobCompletionNotificationListener jobCompletionNotificationListener() {
    return new JobCompletionNotificationListener();
  }

  public static class JobCompletionNotificationListener extends JobExecutionListenerSupport {

    @Override
    public void afterJob(JobExecution jobExecution) {
      if (jobExecution.getStatus() == BatchStatus.COMPLETED) {
        log.info("ElasticSearch Sync Completed Successfully");
      }
    }
  }
}
