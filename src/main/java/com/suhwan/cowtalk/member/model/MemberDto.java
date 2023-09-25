package com.suhwan.cowtalk.member.model;

import com.suhwan.cowtalk.member.entity.Member;
import com.suhwan.cowtalk.member.type.Roles;
import java.time.LocalDateTime;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
public class MemberDto {

  private Long id;
  private String email;
  private String password;
  private String nickname;
  private long experience;
  private String pictureUrl;
  private String uuid;
  private boolean emailAuthYn;
  private Roles roles;
  private LocalDateTime createDateTime;
  private LocalDateTime updateDateTime;
  private LocalDateTime deleteDateTime;

  public static MemberDto fromEntity(Member member) {

    return MemberDto.builder()
        .id(member.getId())
        .email(member.getEmail())
        .password(member.getPassword())
        .nickname(member.getNickname())
        .experience(member.getExperience())
        .pictureUrl(member.getPictureUrl())
        .uuid(member.getUuid())
        .emailAuthYn(member.isEmailAuthYn())
        .roles(member.getRoles())
        .createDateTime(member.getCreateDateTime())
        .updateDateTime(member.getUpdateDateTime())
        .deleteDateTime(member.getDeleteDateTime())
        .build();
  }
}
