package com.suhwan.cowtalk.category.service;

import com.suhwan.cowtalk.category.entity.Category;
import com.suhwan.cowtalk.category.model.AddCategoryRequest;
import com.suhwan.cowtalk.category.model.CategoryDto;
import com.suhwan.cowtalk.category.model.UpdateCategoryRequest;
import com.suhwan.cowtalk.category.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

  // 카테고리 수정
  @Transactional
  public CategoryDto updateCategory(Long id, UpdateCategoryRequest request) {
    Category category = categoryRepository.findById(id)
        .orElseThrow(() -> new IllegalStateException("찾을 수 없는 카테고리 번호입니다."));

    // 이름만 바꿨을 경우
    if (!request.getName().equals(category.getName()) &&
        categoryRepository.existsByName(request.getName())) {
      throw new IllegalStateException("이미 존재하는 카테고리입니다.");
    }

    category.update(request.getName(), request.getIsReadOnly());

    return CategoryDto.fromEntity(category);
  }

  // 카테고리 삭제
  @Transactional
  public CategoryDto deleteCategory(Long id) {
    Category category = categoryRepository.findById(id)
        .orElseThrow(() -> new IllegalStateException("찾을 수 없는 카테고리 번호입니다."));

    if (category.getDeleteDateTime() != null) {
      throw new IllegalStateException("이미 삭제된 카테고리 번호입니다.");
    }

    category.delete();

    return CategoryDto.fromEntity(category);
  }
}
