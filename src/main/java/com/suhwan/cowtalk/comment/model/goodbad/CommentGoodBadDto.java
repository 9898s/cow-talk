package com.suhwan.cowtalk.comment.model.goodbad;

import com.suhwan.cowtalk.comment.entity.Comment;
import com.suhwan.cowtalk.comment.entity.CommentGoodBad;
import com.suhwan.cowtalk.common.type.GoodBad;
import com.suhwan.cowtalk.member.entity.Member;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
public class CommentGoodBadDto {

  private Long id;
  private GoodBad goodBad;
  private Comment comment;
  private Member member;
  private LocalDateTime createDateTime;

  public static CommentGoodBadDto fromEntity(CommentGoodBad commentGoodBad) {

    return CommentGoodBadDto.builder()
        .id(commentGoodBad.getId())
        .goodBad(commentGoodBad.getGoodBad())
        .comment(commentGoodBad.getComment())
        .member(commentGoodBad.getMember())
        .createDateTime(commentGoodBad.getCreateDateTime())
        .build();
  }
}
