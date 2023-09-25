package com.suhwan.cowtalk.member.repository;

import com.suhwan.cowtalk.member.entity.Member;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberRepository extends JpaRepository<Member, Long> {

  Optional<Member> findByEmail(String email);

  Optional<Member> findByUuid(String uuid);

  boolean existsByEmail(String email);

  boolean existsByNickname(String nickname);
}
