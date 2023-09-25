package com.suhwan.cowtalk.category.model;

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
public class AddCategoryResponse {

  private Long id;
  private String name;
  private Boolean isReadOnly;

  @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
  private LocalDateTime createDateTime;

  public static AddCategoryResponse from(CategoryDto categoryDto) {

    return AddCategoryResponse.builder()
        .id(categoryDto.getId())
        .name(categoryDto.getName())
        .isReadOnly(categoryDto.getIsReadOnly())
        .createDateTime(categoryDto.getCreateDateTime())
        .build();
  }
}
