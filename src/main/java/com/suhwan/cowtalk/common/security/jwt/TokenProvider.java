package com.suhwan.cowtalk.common.security.jwt;

import com.suhwan.cowtalk.common.security.jwt.refreshToken.RefreshToken;
import com.suhwan.cowtalk.common.security.jwt.refreshToken.RefreshTokenRepository;
import com.suhwan.cowtalk.member.service.MemberDetailService;
import com.suhwan.cowtalk.member.type.Roles;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import java.util.Date;
import java.util.Optional;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

@RequiredArgsConstructor
@Component
public class TokenProvider {

  private static final long TOKEN_EXPIRE_TIME = 1000 * 60 * 30; // 30분
  private static final String KEY_ROLES = "roles";

  private final MemberDetailService memberDetailService;
  private final RefreshTokenRepository refreshTokenRepository;

  @Value("{spring.jwt.secret}")
  private String secretKey;

  public String createToken(String username, Roles roles) {
    Claims claims = Jwts.claims().setSubject(username);
    claims.put(KEY_ROLES, roles);

    Date now = new Date();
    Date expiredDate = new Date(now.getTime() + TOKEN_EXPIRE_TIME);

    return Jwts.builder()
        .setClaims(claims)
        .setIssuedAt(now)
        .setExpiration(expiredDate)
        .signWith(SignatureAlgorithm.HS512, secretKey)
        .compact();
  }

  public String createRefreshToken(String memberEmail) {
    RefreshToken refreshToken = refreshTokenRepository.save(RefreshToken.builder()
        .refreshToken(UUID.randomUUID().toString())
        .memberEmail(memberEmail)
        .build());

    return refreshToken.getRefreshToken();
  }

  public Authentication getAuthentication(String jwt) {
    UserDetails userDetails = memberDetailService.loadUserByUsername(getUserName(jwt));
    return new UsernamePasswordAuthenticationToken(userDetails, "", userDetails.getAuthorities());
  }

  public String getUserName(String token) {
    return parseClaims(token).getSubject();
  }

  public boolean validateToken(String token) {
    if (!StringUtils.hasText(token)) {
      return false;
    }

    Claims claims = parseClaims(token);
    return !claims.getExpiration().before(new Date());
  }

  private Claims parseClaims(String token) {
    try {
      return Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token).getBody();
    } catch (ExpiredJwtException e) {
      return e.getClaims();
    }
  }
}
