package com.suhwan.cowtalk.category.controller;

import com.suhwan.cowtalk.category.model.AddCategoryRequest;
import com.suhwan.cowtalk.category.model.AddCategoryResponse;
import com.suhwan.cowtalk.category.model.CategoryDto;
import com.suhwan.cowtalk.category.model.DeleteCategoryResponse;
import com.suhwan.cowtalk.category.model.UpdateCategoryRequest;
import com.suhwan.cowtalk.category.model.UpdateCategoryResponse;
import com.suhwan.cowtalk.category.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RequestMapping("/api/category")
@RestController
public class CategoryController {

  private final CategoryService categoryService;

  @PostMapping
  public ResponseEntity<?> addCategory(@RequestBody AddCategoryRequest request) {
    CategoryDto categoryDto = categoryService.addCategory(request);

    return ResponseEntity.ok().body(AddCategoryResponse.from(categoryDto));
  }

  @PutMapping("/{id}")
  public ResponseEntity<?> updateCategory(@PathVariable Long id,
      @RequestBody UpdateCategoryRequest request) {
    CategoryDto categoryDto = categoryService.updateCategory(id, request);

    return ResponseEntity.ok().body(UpdateCategoryResponse.from(categoryDto));
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<?> deleteCategory(@PathVariable Long id) {
    CategoryDto categoryDto = categoryService.deleteCategory(id);

    return ResponseEntity.ok().body(DeleteCategoryResponse.from(categoryDto));
  }
}
