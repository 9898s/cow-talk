package com.suhwan.cowtalk.comment.controller;

import com.suhwan.cowtalk.comment.model.CommentDto;
import com.suhwan.cowtalk.comment.model.WriteCommentRequest;
import com.suhwan.cowtalk.comment.model.WriteCommentResponse;
import com.suhwan.cowtalk.comment.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RequestMapping("/api/comment")
@RestController
public class CommentController {

  private final CommentService commentService;

  @PostMapping
  public ResponseEntity<?> writeComment(@RequestBody WriteCommentRequest request) {
    CommentDto commentDto = commentService.writeComment(request);

    return ResponseEntity.ok().body(WriteCommentResponse.from(commentDto));
  }
}
