package com.suhwan.cowtalk.comment.controller;

import com.suhwan.cowtalk.comment.model.CommentDto;
import com.suhwan.cowtalk.comment.model.CommentResponse;
import com.suhwan.cowtalk.comment.model.CommentResponseList;
import com.suhwan.cowtalk.comment.model.DeleteCommentResponse;
import com.suhwan.cowtalk.comment.model.UpdateCommentRequest;
import com.suhwan.cowtalk.comment.model.UpdateCommentResponse;
import com.suhwan.cowtalk.comment.model.WriteCommentRequest;
import com.suhwan.cowtalk.comment.model.WriteCommentResponse;
import com.suhwan.cowtalk.comment.service.CommentService;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
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

  @GetMapping("/{postId}")
  public ResponseEntity<?> getPostComment(@PathVariable Long postId) {
    List<CommentDto> commentDtoList = commentService.getPostComment(postId);

    List<CommentResponse> commentResponseList = commentDtoList.stream()
        .map(CommentResponse::from)
        .collect(Collectors.toList());

    return ResponseEntity.ok()
        .body(CommentResponseList.of(commentResponseList.size(), commentResponseList));
  }

  @PutMapping("/{id}")
  public ResponseEntity<?> updateComment(@PathVariable Long id,
      @RequestBody UpdateCommentRequest request) {
    CommentDto commentDto = commentService.updateComment(id, request);

    return ResponseEntity.ok().body(UpdateCommentResponse.from(commentDto));
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<?> deleteComment(@PathVariable Long id) {
    CommentDto commentDto = commentService.deleteComment(id);

    return ResponseEntity.ok().body(DeleteCommentResponse.from(commentDto));
  }
}
