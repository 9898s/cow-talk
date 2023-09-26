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
public class UpdateCategoryResponse {

  private Long id;
  private String name;
  private Boolean isReadOnly;

  @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
  private LocalDateTime updateDateTime;

  public static UpdateCategoryResponse from(CategoryDto categoryDto) {

    return UpdateCategoryResponse.builder()
        .id(categoryDto.getId())
        .name(categoryDto.getName())
        .isReadOnly(categoryDto.getIsReadOnly())
        .updateDateTime(categoryDto.getUpdateDateTime())
        .build();
  }
}
