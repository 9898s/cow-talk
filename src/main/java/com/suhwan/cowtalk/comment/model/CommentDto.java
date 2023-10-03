package com.suhwan.cowtalk.comment.model;

import com.suhwan.cowtalk.comment.entity.Comment;
import com.suhwan.cowtalk.member.entity.Member;
import com.suhwan.cowtalk.post.entity.Post;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
public class CommentDto {

  private Long id;
  private String content;
  private boolean isBlind;
  private Post post;
  private Member member;
  private LocalDateTime createDateTime;
  private LocalDateTime updateDateTime;
  private LocalDateTime deleteDateTime;

  public static CommentDto fromEntity(Comment comment) {

    return CommentDto.builder()
        .id(comment.getId())
        .content(comment.getContent())
        .isBlind(comment.isBlind())
        .post(comment.getPost())
        .member(comment.getMember())
        .createDateTime(comment.getCreateDateTime())
        .updateDateTime(comment.getUpdateDateTime())
        .deleteDateTime(comment.getDeleteDateTime())
        .build();
  }
}
