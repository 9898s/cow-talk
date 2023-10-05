package com.suhwan.cowtalk.comment.service;

import com.suhwan.cowtalk.comment.model.CommentDto;
import com.suhwan.cowtalk.comment.model.CommentResponse;
import com.suhwan.cowtalk.common.type.GoodBad;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class CommentApiService {

  private final CommentService commentService;
  private final CommentGoodBadService commentGoodBadService;

  public List<CommentResponse> getCommentResponseList(Long postId) {
    List<CommentDto> commentDtoList = commentService.getPostComment(postId);

    return commentDtoList.stream()
        .map(commentDto -> {
          Long goodCount = commentGoodBadService.countGoodBad(commentDto.getId(), GoodBad.GOOD);
          Long badCount = commentGoodBadService.countGoodBad(commentDto.getId(), GoodBad.BAD);
          return CommentResponse.from(commentDto, goodCount, badCount);
        })
        .collect(Collectors.toList());
  }
}
