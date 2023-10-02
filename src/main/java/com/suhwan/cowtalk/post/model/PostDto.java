package com.suhwan.cowtalk.post.model;

import com.suhwan.cowtalk.category.entity.Category;
import com.suhwan.cowtalk.member.entity.Member;
import com.suhwan.cowtalk.post.entity.Post;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
public class PostDto {

  private Long id;
  private String title;
  private String content;
  private long view;
  private Boolean isBlind;
  private Category category;
  private Member member;
  private LocalDateTime createDateTime;
  private LocalDateTime updateDateTime;
  private LocalDateTime deleteDateTime;

  public static PostDto fromEntity(Post post) {

    return PostDto.builder()
        .id(post.getId())
        .title(post.getTitle())
        .content(post.getContent())
        .view(post.getView())
        .isBlind(post.isBlind())
        .category(post.getCategory())
        .member(post.getMember())
        .createDateTime(post.getCreateDateTime())
        .updateDateTime(post.getUpdateDateTime())
        .deleteDateTime(post.getDeleteDateTime())
        .build();
  }
}
