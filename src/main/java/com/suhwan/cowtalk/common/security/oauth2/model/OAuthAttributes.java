package com.suhwan.cowtalk.common.security.oauth2.model;

import com.suhwan.cowtalk.member.entity.Member;
import com.suhwan.cowtalk.member.type.Roles;
import java.util.Map;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
public class OAuthAttributes {

  private Map<String, Object> attributes;
  private String nameAttributeKey;
  private String email;
  private String nickname;
  private String pictureUrl;

  public static OAuthAttributes of(String registrationId, String userNameAttributeName,
      Map<String, Object> attributes) {
    if ("naver".equals(registrationId)) {
      return ofNaver("id", attributes);
    } else if ("kakao".equals(registrationId)) {
      return ofKakao("id", attributes);
    }
    return ofGoogle(userNameAttributeName, attributes);
  }

  private static OAuthAttributes ofNaver(String userNameAttributeName,
      Map<String, Object> attributes) {
    Map<String, Object> response = (Map<String, Object>) attributes.get("response");

    return OAuthAttributes.builder()
        .email((String) response.get("email"))
        .nickname((String) response.get("name"))
        .pictureUrl((String) response.get("profile_image"))
        .attributes(response)
        .nameAttributeKey(userNameAttributeName)
        .build();
  }

  private static OAuthAttributes ofKakao(String userNameAttributeName,
      Map<String, Object> attributes) {
    Map<String, Object> response = (Map<String, Object>) attributes.get("kakao_account");
    Map<String, Object> profile = (Map<String, Object>) response.get("profile");

    return OAuthAttributes.builder()
        .email((String) response.get("email"))
        .nickname((String) profile.get("nickname"))
        .pictureUrl((String) profile.get("profile_image_url"))
        .attributes(attributes)
        .nameAttributeKey(userNameAttributeName)
        .build();
  }

  private static OAuthAttributes ofGoogle(String userNameAttributeName,
      Map<String, Object> attributes) {
    return OAuthAttributes.builder()
        .email((String) attributes.get("email"))
        .nickname((String) attributes.get("name"))
        .pictureUrl((String) attributes.get("picture"))
        .attributes(attributes)
        .nameAttributeKey(userNameAttributeName)
        .build();
  }

  public Member toEntity() {
    return Member.builder()
        .email(email)
        .nickname(nickname)
        .pictureUrl(pictureUrl)
        .roles(Roles.ROLE_USER)
        .build();
  }
}
