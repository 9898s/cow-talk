package com.suhwan.cowtalk.category.entity;

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
public class Category extends BaseTimeEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(unique = true)
  private String name;

  @Column
  private Boolean isReadOnly;

  @Column
  private LocalDateTime updateDateTime;

  @Column
  private LocalDateTime deleteDateTime;

  public Category update(String name, Boolean isReadOnly) {
    this.name = name;
    this.isReadOnly = isReadOnly;
    this.updateDateTime = LocalDateTime.now();

    return this;
  }
}
