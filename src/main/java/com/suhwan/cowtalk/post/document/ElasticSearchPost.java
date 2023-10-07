package com.suhwan.cowtalk.post.document;

import com.suhwan.cowtalk.member.entity.Member;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

@Document(indexName = "post")
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
public class ElasticSearchPost {

  @Id
  private Long id;

  private String title;
  private String content;
  private long view;
  private boolean isBlind;

  @Field(type = FieldType.Object)
  private Member member;
}
