package com.suhwan.cowtalk.comment.repository;

import com.suhwan.cowtalk.comment.entity.Comment;
import com.suhwan.cowtalk.comment.entity.CommentGoodBad;
import com.suhwan.cowtalk.common.type.GoodBad;
import com.suhwan.cowtalk.member.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentGoodBadRepository extends JpaRepository<CommentGoodBad, Long> {

  boolean existsByCommentAndMember(Comment comment, Member member);

  long countByCommentAndGoodBad(Comment comment, GoodBad goodBad);
}
