package com.suhwan.cowtalk.post.model.goodbad;

import com.suhwan.cowtalk.common.type.GoodBad;
import com.suhwan.cowtalk.member.entity.Member;
import com.suhwan.cowtalk.post.entity.Post;
import com.suhwan.cowtalk.post.entity.PostGoodBad;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
public class PostGoodBadDto {

  private Long id;
  private GoodBad goodBad;
  private Post post;
  private Member member;
  private LocalDateTime createDateTime;

  public static PostGoodBadDto fromEntity(PostGoodBad postGoodBad) {

    return PostGoodBadDto.builder()
        .id(postGoodBad.getId())
        .goodBad(postGoodBad.getGoodBad())
        .post(postGoodBad.getPost())
        .member(postGoodBad.getMember())
        .createDateTime(postGoodBad.getCreateDateTime())
        .build();
  }
}
