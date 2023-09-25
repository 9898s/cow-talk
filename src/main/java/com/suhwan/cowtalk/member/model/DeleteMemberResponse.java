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
public class DeleteMemberResponse {

  private Long id;

  @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
  private LocalDateTime deleteDateTime;

  public static DeleteMemberResponse from(MemberDto memberDto) {

    return DeleteMemberResponse.builder()
        .id(memberDto.getId())
        .deleteDateTime(memberDto.getDeleteDateTime())
        .build();
  }
}
