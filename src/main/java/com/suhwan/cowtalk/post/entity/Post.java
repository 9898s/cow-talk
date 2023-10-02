package com.suhwan.cowtalk.post.entity;

import com.suhwan.cowtalk.category.entity.Category;
import com.suhwan.cowtalk.common.entity.BaseTimeEntity;
import com.suhwan.cowtalk.member.entity.Member;
import java.time.LocalDateTime;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Entity
public class Post extends BaseTimeEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(nullable = false)
  private String title;

  @Column(nullable = false, columnDefinition = "TEXT")
  private String content;

  @Column
  private long view;

  @Column
  private boolean isBlind;

  @ManyToOne
  @JoinColumn(name = "category_id")
  private Category category;

  @ManyToOne
  @JoinColumn(name = "member_id")
  private Member member;

  @Column
  private LocalDateTime updateDateTime;

  @Column
  private LocalDateTime deleteDateTime;

  public Post update(String title, String content) {
    this.title = title;
    this.content = content;
    this.updateDateTime = LocalDateTime.now();

    return this;
  }

  public Post delete() {
    this.deleteDateTime = LocalDateTime.now();

    return this;
  }

  public Post addView() {
    this.view += 1;

    return this;
  }

  public Post blind() {
    this.isBlind = true;

    return this;
  }
}
