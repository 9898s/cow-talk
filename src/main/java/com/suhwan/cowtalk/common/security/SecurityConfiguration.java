package com.suhwan.cowtalk.common.security;

import com.suhwan.cowtalk.common.security.jwt.JwtAuthenticationFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@RequiredArgsConstructor
@EnableWebSecurity
@Configuration
public class SecurityConfiguration {

  private final JwtAuthenticationFilter authenticationFilter;

  @Bean
  public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
    return http
        .csrf()
        .disable()

        .headers()
        .frameOptions()
        .disable()

        .and()
        .sessionManagement()
        .sessionCreationPolicy(SessionCreationPolicy.STATELESS)

        .and()
        .authorizeHttpRequests()
        .antMatchers("/api/member/**").permitAll()
        .antMatchers("/api/exchange/**", "/api/coin/**").hasRole("USER")
        .requestMatchers(PathRequest.toH2Console()).permitAll()
        .anyRequest().authenticated()

        .and()
        .addFilterBefore(authenticationFilter, UsernamePasswordAuthenticationFilter.class)
        .build();
  }

  @Bean
  public BCryptPasswordEncoder bCryptPasswordEncoder() {
    return new BCryptPasswordEncoder();
  }
}
