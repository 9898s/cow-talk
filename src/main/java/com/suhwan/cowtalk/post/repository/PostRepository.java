package com.suhwan.cowtalk.post.repository;

import com.suhwan.cowtalk.post.entity.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostRepository extends JpaRepository<Post, Long> {

  Page<Post> findAll(Pageable pageable);
}