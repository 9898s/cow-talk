package com.suhwan.cowtalk.common.configuration;

import com.suhwan.cowtalk.post.repository.ElasticSearchPostRepository;
import org.elasticsearch.client.RestHighLevelClient;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.elasticsearch.client.ClientConfiguration;
import org.springframework.data.elasticsearch.client.RestClients;
import org.springframework.data.elasticsearch.config.AbstractElasticsearchConfiguration;
import org.springframework.data.elasticsearch.repository.config.EnableElasticsearchRepositories;

@EnableElasticsearchRepositories(basePackageClasses = {ElasticSearchPostRepository.class})
@Configuration
public class ElasticSearchConfiguration extends AbstractElasticsearchConfiguration {

  @Override
  public RestHighLevelClient elasticsearchClient() {
    ClientConfiguration clientConfiguration = ClientConfiguration.builder()
        .connectedTo("localhost:9200")
        .build();

    return RestClients.create(clientConfiguration).rest();
  }
}
