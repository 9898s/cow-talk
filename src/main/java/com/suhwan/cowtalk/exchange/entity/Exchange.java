package com.suhwan.cowtalk.exchange.entity;

import com.suhwan.cowtalk.common.entity.BaseTimeEntity;
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
public class Exchange extends BaseTimeEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column
  private String koreanName;

  @Column
  private String englishName;

  @Column
  private LocalDateTime updateDateTime;

  @Column
  private LocalDateTime deleteDateTime;

  public void update(String koreanName, String englishName, LocalDateTime updateDateTime) {
    this.koreanName = koreanName;
    this.englishName = englishName;
    this.updateDateTime = updateDateTime;
  }

  public void delete(LocalDateTime deleteDateTime) {
    this.deleteDateTime = deleteDateTime;
  }
}
