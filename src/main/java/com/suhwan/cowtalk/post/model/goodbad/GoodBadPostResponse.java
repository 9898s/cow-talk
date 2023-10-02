package com.suhwan.cowtalk.post.model.goodbad;

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
public class GoodBadPostResponse {

  private Long id;
  private Long postId;
  private Long memberId;
  private GoodBad goodBad;

  @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
  private LocalDateTime createDateTime;

  public static GoodBadPostResponse from(PostGoodBadDto postGoodBadDto) {

    return GoodBadPostResponse.builder()
        .id(postGoodBadDto.getId())
        .postId(postGoodBadDto.getPost().getId())
        .memberId(postGoodBadDto.getMember().getId())
        .goodBad(postGoodBadDto.getGoodBad())
        .createDateTime(postGoodBadDto.getCreateDateTime())
        .build();
  }
}
