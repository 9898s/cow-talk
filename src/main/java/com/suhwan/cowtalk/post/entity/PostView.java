package com.suhwan.cowtalk.post.entity;

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
@RedisHash(value = "postview", timeToLive = 60 * 60 * 24)
public class PostView {

  @Id
  private String id;

  private Long postId;
  private String ip;
}
