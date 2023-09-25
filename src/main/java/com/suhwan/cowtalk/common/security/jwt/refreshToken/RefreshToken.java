package com.suhwan.cowtalk.common.security.jwt.refreshToken;

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
@RedisHash(value = "refreshToken", timeToLive = 60 * 60 * 24 * 14)
public class RefreshToken {

  @Id
  private String refreshToken;

  private String memberEmail;
}
