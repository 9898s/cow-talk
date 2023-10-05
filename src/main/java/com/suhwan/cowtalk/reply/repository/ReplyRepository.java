package com.suhwan.cowtalk.reply.repository;

import com.suhwan.cowtalk.comment.entity.Comment;
import com.suhwan.cowtalk.reply.entity.Reply;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReplyRepository extends JpaRepository<Reply, Long> {

  List<Reply> findAllByComment(Comment comment);
}
