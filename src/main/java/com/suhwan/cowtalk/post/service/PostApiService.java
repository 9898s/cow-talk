package com.suhwan.cowtalk.post.service;

import com.suhwan.cowtalk.common.type.GoodBad;
import com.suhwan.cowtalk.post.model.PostDto;
import com.suhwan.cowtalk.post.model.PostResponse;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class PostApiService {

  private final PostService postService;
  private final PostGoodBadService postGoodBadService;

  public List<PostResponse> getPostResponseList(int page, int size) {
    List<PostDto> postDtoList = postService.getAllPost(page, size);

    return createPostResponseList(postDtoList);
  }

  public List<PostResponse> getCategoryPostResponseList(Long categoryId, int page, int size) {
    List<PostDto> postDtoList = postService.getAllCategoryPost(categoryId, page, size);
    return createPostResponseList(postDtoList);
  }

  public List<PostResponse> getHotPostResponseList(int page, int size, LocalDateTime startDateTime, LocalDateTime endDateTime) {
    List<PostDto> postDtoList = postService.hotPost(page, size, startDateTime, endDateTime);
    return createPostResponseList(postDtoList);
  }

  private List<PostResponse> createPostResponseList(List<PostDto> postDtoList) {
    List<PostResponse> postResponseList = new ArrayList<>();

    for (PostDto postDto : postDtoList) {
      Long goodCount = postGoodBadService.countGoodBad(postDto.getId(), GoodBad.GOOD);
      Long badCount = postGoodBadService.countGoodBad(postDto.getId(), GoodBad.BAD);

      postResponseList.add(PostResponse.from(postDto, goodCount, badCount));
    }
    return postResponseList;
  }
}
