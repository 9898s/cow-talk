package com.suhwan.cowtalk.member.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
public class AuthMemberResponse {

  private Long id;
  private String email;
  private String nickname;
  private boolean emailAuthYn;

  public static AuthMemberResponse from(MemberDto memberDto) {

    return AuthMemberResponse.builder()
        .id(memberDto.getId())
        .email(memberDto.getEmail())
        .nickname(memberDto.getNickname())
        .emailAuthYn(memberDto.isEmailAuthYn())
        .build();
  }
}
