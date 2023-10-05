package com.suhwan.cowtalk.member.service;

import static com.suhwan.cowtalk.common.type.ErrorCode.INVALID_MEMBER_EMAIL;

import com.suhwan.cowtalk.common.exception.MemberException;
import com.suhwan.cowtalk.member.entity.Member;
import com.suhwan.cowtalk.member.entity.MemberDetails;
import com.suhwan.cowtalk.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class MemberDetailService implements UserDetailsService {

  private final MemberRepository memberRepository;

  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    Member member = memberRepository.findByEmail(username)
        .orElseThrow(() -> new MemberException(INVALID_MEMBER_EMAIL));

    return new MemberDetails(member);
  }
}
