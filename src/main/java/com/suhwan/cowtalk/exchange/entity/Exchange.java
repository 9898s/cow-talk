package com.suhwan.cowtalk.exchange.entity;

import java.time.LocalDateTime;
import javax.persistence.Column;
import javax.persistence.Entity;
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
public class Exchange {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column
  private String koreanName;

  @Column
  private String englishName;

  @Column
  private LocalDateTime createDate;

  @Column
  private LocalDateTime updateDate;

  @Column
  private LocalDateTime deleteDate;

  public void update(String koreanName, String englishName, LocalDateTime updateDate) {
    this.koreanName = koreanName;
    this.englishName = englishName;
    this.updateDate = updateDate;
  }

  public void delete(LocalDateTime deleteDate) {
    this.deleteDate = deleteDate;
  }
}
