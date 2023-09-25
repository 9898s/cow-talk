package com.suhwan.cowtalk.category.repository;

import com.suhwan.cowtalk.category.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, Long> {

  boolean existsByName(String name);
}
