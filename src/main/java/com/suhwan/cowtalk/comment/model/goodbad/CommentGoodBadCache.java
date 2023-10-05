package com.suhwan.cowtalk.comment.model.goodbad;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@RedisHash(value = "commentgoodbadcache")
public class CommentGoodBadCache {

  @Id
  private String id;

  private Long commentId;
  private Long memberId;
}
