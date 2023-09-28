package com.suhwan.cowtalk.post.repository;

import com.suhwan.cowtalk.category.entity.Category;
import com.suhwan.cowtalk.post.entity.Post;
import java.time.LocalDateTime;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostRepository extends JpaRepository<Post, Long> {

  Page<Post> findAll(Pageable pageable);

  Page<Post> findAllByCategory(Category category, Pageable pageable);

  Page<Post> findAllByCreateDateTimeBetween(Pageable pageable, LocalDateTime startDateTime,
      LocalDateTime endDateTime);
}