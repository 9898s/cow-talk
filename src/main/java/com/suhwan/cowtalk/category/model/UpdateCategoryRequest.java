package com.suhwan.cowtalk.category.model;

import lombok.Getter;

@Getter
public class UpdateCategoryRequest {

  private String name;
  private Boolean isReadOnly;
}
