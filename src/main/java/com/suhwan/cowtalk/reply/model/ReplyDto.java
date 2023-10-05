package com.suhwan.cowtalk.reply.model;

import com.suhwan.cowtalk.comment.entity.Comment;
import com.suhwan.cowtalk.member.entity.Member;
import com.suhwan.cowtalk.reply.entity.Reply;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
public class ReplyDto {

  private Long id;
  private String content;
  private boolean isBlind;
  private Comment comment;
  private Member member;
  private LocalDateTime createDateTime;
  private LocalDateTime updateDateTime;
  private LocalDateTime deleteDateTime;

  public static ReplyDto fromEntity(Reply reply) {

    return ReplyDto.builder()
        .id(reply.getId())
        .content(reply.getContent())
        .isBlind(reply.isBlind())
        .comment(reply.getComment())
        .member(reply.getMember())
        .createDateTime(reply.getCreateDateTime())
        .updateDateTime(reply.getUpdateDateTime())
        .deleteDateTime(reply.getDeleteDateTime())
        .build();
  }
}
