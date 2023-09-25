package com.suhwan.cowtalk.category.model;

import lombok.Getter;

@Getter
public class AddCategoryRequest {

  private String name;
  private Boolean isReadOnly;
}
