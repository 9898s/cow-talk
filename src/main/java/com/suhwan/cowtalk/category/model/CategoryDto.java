package com.suhwan.cowtalk.category.model;

import com.suhwan.cowtalk.category.entity.Category;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
public class CategoryDto {

  private Long id;
  private String name;
  private Boolean isReadOnly;
  private LocalDateTime createDateTime;
  private LocalDateTime updateDateTime;
  private LocalDateTime deleteDateTime;

  public static CategoryDto fromEntity(Category category) {
    return CategoryDto.builder()
        .id(category.getId())
        .name(category.getName())
        .isReadOnly(category.getIsReadOnly())
        .createDateTime(category.getCreateDateTime())
        .updateDateTime(category.getUpdateDateTime())
        .deleteDateTime(category.getDeleteDateTime())
        .build();
  }
}
