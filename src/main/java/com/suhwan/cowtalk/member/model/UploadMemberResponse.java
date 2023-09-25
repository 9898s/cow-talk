package com.suhwan.cowtalk.member.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
public class UploadMemberResponse {

  private Long id;
  private String pictureUrl;

  public static UploadMemberResponse from(MemberDto memberDto) {

    return UploadMemberResponse.builder()
        .id(memberDto.getId())
        .pictureUrl(memberDto.getPictureUrl())
        .build();
  }
}
