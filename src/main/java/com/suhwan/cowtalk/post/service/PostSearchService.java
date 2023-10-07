package com.suhwan.cowtalk.post.service;

import com.suhwan.cowtalk.post.document.ElasticSearchPost;
import com.suhwan.cowtalk.post.model.PostDto;
import com.suhwan.cowtalk.post.repository.ElasticSearchPostRepository;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class PostSearchService {

  private final ElasticSearchPostRepository elasticSearchPostRepository;

  public List<PostDto> searchNickname(String nickname) {
    List<ElasticSearchPost> elasticSearchPostList = elasticSearchPostRepository.findAllByMember_Nickname(
        nickname);

    return elasticSearchPostList.stream()
        .map(PostDto::fromDocument)
        .collect(Collectors.toList());
  }

  public List<PostDto> searchTitle(String title) {
    List<ElasticSearchPost> elasticSearchPostList = elasticSearchPostRepository.findAllByTitleContaining(
        title);

    return elasticSearchPostList.stream()
        .map(PostDto::fromDocument)
        .collect(Collectors.toList());
  }
}
