package com.suhwan.cowtalk.category.service;

import com.suhwan.cowtalk.category.entity.Category;
import com.suhwan.cowtalk.category.model.AddCategoryRequest;
import com.suhwan.cowtalk.category.model.CategoryDto;
import com.suhwan.cowtalk.category.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class CategoryService {

  private final CategoryRepository categoryRepository;

  // 카테고리 추가
  public CategoryDto addCategory(AddCategoryRequest request) {
    if (categoryRepository.existsByName(request.getName())) {
      throw new IllegalStateException("이미 존재하는 카테고리입니다.");
    }

    return CategoryDto.fromEntity(
        categoryRepository.save(
            Category.builder()
                .name(request.getName())
                .isReadOnly(request.getIsReadOnly())
                .build()
        )
    );
  }
}
