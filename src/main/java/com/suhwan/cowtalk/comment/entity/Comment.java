package com.suhwan.cowtalk.comment.entity;

import com.suhwan.cowtalk.common.entity.BaseTimeEntity;
import com.suhwan.cowtalk.member.entity.Member;
import com.suhwan.cowtalk.post.entity.Post;
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
public class Comment extends BaseTimeEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(nullable = false)
  private String content;

  @Column
  private boolean isBlind;

  @ManyToOne
  @JoinColumn(name = "post_id")
  private Post post;

  @ManyToOne
  @JoinColumn(name = "member_id")
  private Member member;

  @Column
  private LocalDateTime updateDateTime;

  @Column
  private LocalDateTime deleteDateTime;

  public Comment update(String content) {
    this.content = content;
    this.updateDateTime = LocalDateTime.now();

    return this;
  }

  public Comment delete() {
    this.deleteDateTime = LocalDateTime.now();

    return this;
  }
}
