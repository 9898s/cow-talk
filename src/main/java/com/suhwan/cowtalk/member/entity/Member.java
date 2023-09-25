package com.suhwan.cowtalk.member.entity;

import com.suhwan.cowtalk.member.type.Roles;
import java.time.LocalDateTime;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Entity
public class Member {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(unique = true, nullable = false)
  private String email;

  @Column
  private String password;

  @Column(unique = true, nullable = false)
  private String nickname;

  @Column
  private long experience;

  @Column
  private String pictureUrl;

  @Column
  private String uuid;

  @Column
  private boolean emailAuthYn;

  @Enumerated(EnumType.STRING)
  @Column
  private Roles roles;

  @Column
  private LocalDateTime createDateTime;

  @Column
  private LocalDateTime updateDateTime;

  @Column
  private LocalDateTime deleteDateTime;

  public Member update(String nickname) {
    this.nickname = nickname;
    this.updateDateTime = LocalDateTime.now();

    return this;
  }

  public Member authorization() {
    this.emailAuthYn = true;

    return this;
  }

  public Member delete() {
    this.deleteDateTime = LocalDateTime.now();

    return this;
  }
}
