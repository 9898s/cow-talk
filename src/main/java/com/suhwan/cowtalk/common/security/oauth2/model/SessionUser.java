package com.suhwan.cowtalk.common.security.oauth2.model;

import com.suhwan.cowtalk.member.entity.Member;
import java.io.Serializable;
import lombok.Getter;

@Getter
public class SessionUser implements Serializable {

  private final String email;
  private final String nickname;
  private final String pictureUrl;

  public SessionUser(Member member) {
    this.email = member.getEmail();
    this.nickname = member.getNickname();
    this.pictureUrl = member.getPictureUrl();
  }
}
