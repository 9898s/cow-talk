package com.suhwan.cowtalk.post.controller;

import com.suhwan.cowtalk.post.model.PostDto;
import com.suhwan.cowtalk.post.model.UpdatePostRequest;
import com.suhwan.cowtalk.post.model.UpdatePostResponse;
import com.suhwan.cowtalk.post.model.WritePostRequest;
import com.suhwan.cowtalk.post.model.WritePostResponse;
import com.suhwan.cowtalk.post.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RequestMapping("/api/post")
@RestController
public class PostController {

  private final PostService postService;

  @PostMapping
  public ResponseEntity<?> writePost(@RequestBody WritePostRequest request) {
    PostDto postDto = postService.writePost(request);

    return ResponseEntity.ok().body(WritePostResponse.from(postDto));
  }

  @PutMapping("/{id}")
  public ResponseEntity<?> updatePost(@PathVariable Long id, @RequestBody UpdatePostRequest request) {
    PostDto postDto = postService.updatePost(id, request);

    return ResponseEntity.ok().body(UpdatePostResponse.from(postDto));
  }
}
