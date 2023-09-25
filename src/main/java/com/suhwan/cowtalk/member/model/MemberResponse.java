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
public class MemberResponse {

  private Long id;
  private String email;
  private String password;
  private String nickname;
  private long experience;
  private String pictureUrl;

  @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
  private LocalDateTime createDateTime;

  @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
  private LocalDateTime updateDateTime;

  @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
  private LocalDateTime deleteDateTime;

  public static MemberResponse from(MemberDto memberDto) {

    return MemberResponse.builder()
        .id(memberDto.getId())
        .email(memberDto.getEmail())
        .password(memberDto.getPassword())
        .nickname(memberDto.getNickname())
        .experience(memberDto.getExperience())
        .pictureUrl(memberDto.getPictureUrl())
        .createDateTime(memberDto.getCreateDateTime())
        .updateDateTime(memberDto.getUpdateDateTime())
        .deleteDateTime(memberDto.getDeleteDateTime())
        .build();
  }
}
