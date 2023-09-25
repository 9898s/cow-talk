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
public class UpdateMemberResponse {

  private Long id;
  private String nickname;

  @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
  private LocalDateTime updateDateTime;

  public static UpdateMemberResponse from(MemberDto memberDto) {

    return UpdateMemberResponse.builder()
        .id(memberDto.getId())
        .nickname(memberDto.getNickname())
        .updateDateTime(memberDto.getUpdateDateTime())
        .build();
  }
}
