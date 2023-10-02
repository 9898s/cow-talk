package com.suhwan.cowtalk.common.security;

import lombok.NoArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

@NoArgsConstructor
public class SecurityUtil {

  public static String getLoginMemberEmail() {
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

    if (authentication == null || authentication.getName() == null) {
      throw new IllegalStateException("Security Context에 담긴 내용을 찾을 수 없습니다.");
    }

    return authentication.getName();
  }
}
