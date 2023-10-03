package com.suhwan.cowtalk.comment.repository;

import com.suhwan.cowtalk.comment.entity.Comment;
import com.suhwan.cowtalk.post.entity.Post;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository<Comment, Long> {

  List<Comment> findAllByPost(Post post);
}
