package com.suhwan.cowtalk.comment.model.goodbad;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.suhwan.cowtalk.common.type.GoodBad;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
public class GoodBadCommentResponse {

  private Long id;
  private Long commentId;
  private Long memberId;
  private GoodBad goodBad;

  @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
  private LocalDateTime createDateTime;

  public static GoodBadCommentResponse from(CommentGoodBadDto postGoodBadDto) {

    return GoodBadCommentResponse.builder()
        .id(postGoodBadDto.getId())
        .commentId(postGoodBadDto.getComment().getId())
        .memberId(postGoodBadDto.getMember().getId())
        .goodBad(postGoodBadDto.getGoodBad())
        .createDateTime(postGoodBadDto.getCreateDateTime())
        .build();
  }
}
