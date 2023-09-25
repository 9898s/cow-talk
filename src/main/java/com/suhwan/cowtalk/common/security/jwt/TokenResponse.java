package com.suhwan.cowtalk.common.security.jwt;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
public class TokenResponse {

  private String accessToken;
  private String refreshToken;

  public static TokenResponse of(String accessToken, String refreshToken) {

    return TokenResponse.builder()
        .accessToken(accessToken)
        .refreshToken(refreshToken)
        .build();
  }
}
