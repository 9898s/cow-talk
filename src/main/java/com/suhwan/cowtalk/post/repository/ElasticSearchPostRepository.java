package com.suhwan.cowtalk.post.repository;

import com.suhwan.cowtalk.post.document.ElasticSearchPost;
import java.util.List;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

public interface ElasticSearchPostRepository extends
    ElasticsearchRepository<ElasticSearchPost, Long> {

  List<ElasticSearchPost> findAllByMember_Nickname(String nickname);

  List<ElasticSearchPost> findAllByTitleContaining(String title);
}