package com.suhwan.cowtalk.reply.model.goodbad;

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
@RedisHash(value = "replygoodbadcache")
public class ReplyGoodBadCache {

  @Id
  private String id;

  private Long replyId;
  private Long memberId;
}
