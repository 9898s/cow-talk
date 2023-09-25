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
public class SignUpMemberResponse {

  private Long id;
  private String email;
  private String nickname;

  @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
  private LocalDateTime createDateTime;

  public static SignUpMemberResponse from(MemberDto memberDto) {
    return SignUpMemberResponse.builder()
        .id(memberDto.getId())
        .email(memberDto.getEmail())
        .nickname(memberDto.getNickname())
        .createDateTime(memberDto.getCreateDateTime())
        .build();
  }
}
