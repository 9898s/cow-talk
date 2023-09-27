package com.suhwan.cowtalk.post.repository;

import com.suhwan.cowtalk.common.type.GoodBad;
import com.suhwan.cowtalk.member.entity.Member;
import com.suhwan.cowtalk.post.entity.Post;
import com.suhwan.cowtalk.post.entity.PostGoodBad;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostGoodBadRepository extends JpaRepository<PostGoodBad, Long> {

  boolean existsByPostAndMember(Post post, Member member);

  Long countByGoodBad(GoodBad goodBad);
}