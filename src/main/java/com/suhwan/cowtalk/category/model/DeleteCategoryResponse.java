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
public class DeleteCategoryResponse {

  private Long id;
  private String name;

  @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
  private LocalDateTime deleteDateTime;

  public static DeleteCategoryResponse from(CategoryDto categoryDto) {

    return DeleteCategoryResponse.builder()
        .id(categoryDto.getId())
        .name(categoryDto.getName())
        .deleteDateTime(categoryDto.getDeleteDateTime())
        .build();
  }
}
