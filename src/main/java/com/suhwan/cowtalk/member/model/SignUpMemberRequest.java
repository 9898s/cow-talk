package com.suhwan.cowtalk.member.model;

import java.time.LocalDateTime;
import lombok.Getter;

@Getter
public class SignUpMemberRequest {

  private String email;
  private String password;
  private String nickname;
  private LocalDateTime createDateTime;
}
