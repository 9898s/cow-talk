package com.suhwan.cowtalk.common.security.jwt.refreshToken;

import java.util.Optional;
import org.springframework.data.repository.CrudRepository;

public interface RefreshTokenRepository extends CrudRepository<RefreshToken, String> {

  Optional<RefreshToken> findByMemberEmail(String memberEmail);
}
