package com.suhwan.cowtalk.common.security.jwt;

import lombok.Getter;

@Getter
public class TokenRequest {

  private String accessToken;
  private String refreshToken;
}
