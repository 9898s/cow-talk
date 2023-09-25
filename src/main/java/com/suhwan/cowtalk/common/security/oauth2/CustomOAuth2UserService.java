package com.suhwan.cowtalk.common.security.oauth2;

import com.suhwan.cowtalk.common.security.oauth2.model.OAuthAttributes;
import com.suhwan.cowtalk.common.security.oauth2.model.SessionUser;
import com.suhwan.cowtalk.member.entity.Member;
import com.suhwan.cowtalk.member.repository.MemberRepository;
import java.util.Collections;
import javax.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class CustomOAuth2UserService implements OAuth2UserService<OAuth2UserRequest, OAuth2User> {

  private final MemberRepository memberRepository;
  private final HttpSession httpSession;

  @Override
  public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
    OAuth2UserService<OAuth2UserRequest, OAuth2User> oAuth2UserService = new DefaultOAuth2UserService();
    OAuth2User oAuth2User = oAuth2UserService.loadUser(userRequest);

    String registrationId = userRequest.getClientRegistration().getRegistrationId();
    String userNameAttribute = userRequest.getClientRegistration().getProviderDetails()
        .getUserInfoEndpoint().getUserNameAttributeName();

    OAuthAttributes attributes = OAuthAttributes.of(registrationId, userNameAttribute,
        oAuth2User.getAttributes());

    Member member = saveOrUpdate(attributes);
    httpSession.setAttribute("member", new SessionUser(member));

    return new DefaultOAuth2User(
        Collections.singleton(new SimpleGrantedAuthority(member.getRoles().name())),
        attributes.getAttributes(), attributes.getNameAttributeKey());
  }

  private Member saveOrUpdate(OAuthAttributes attributes) {
    Member member = memberRepository.findByEmail(attributes.getEmail())
        .map(entity -> entity.update(attributes.getNickname(), attributes.getPictureUrl()))
        .orElse(attributes.toEntity());

    return memberRepository.save(member);
  }
}
