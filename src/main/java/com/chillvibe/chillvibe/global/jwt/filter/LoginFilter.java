package com.chillvibe.chillvibe.global.jwt.filter;

import com.chillvibe.chillvibe.domain.user.dto.LoginRequestDto;
import com.chillvibe.chillvibe.global.jwt.dto.CustomUserDetails;
import com.chillvibe.chillvibe.global.jwt.entity.Refresh;
import com.chillvibe.chillvibe.global.jwt.repository.RefreshRepository;
import com.chillvibe.chillvibe.global.jwt.util.JwtUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletInputStream;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.util.StreamUtils;

@RequiredArgsConstructor
public class LoginFilter extends UsernamePasswordAuthenticationFilter {

  private static final long ACCESS_TOKEN_EXPIRATION_MS = 60*60*60*10L;
  private static final long REFRESH_TOKEN_EXPIRATION_MS = 60*60*60*24L;

  private final AuthenticationManager authenticationManager;
  private final JwtUtil jwtUtil;
  private final RefreshRepository refreshRepository;

  @Override
  public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {

    LoginRequestDto loginDto = new LoginRequestDto();

    // 요청 본문에서 JSON 데이터 읽기
    try {

      // JSON 데이터를 객체로 변환하기 위한 ObjectMapper
      ObjectMapper objectMapper = new ObjectMapper();
      // request 요청 본문을 바이트 스트림으로 읽음
      ServletInputStream inputStream = request.getInputStream();
      // 바이트 스트림을 UTF-8 문자열로 변환
      String messageBody = StreamUtils.copyToString(inputStream, StandardCharsets.UTF_8);
      // JSON 문자열을 LoginDto 객체로 변환
      loginDto = objectMapper.readValue(messageBody, LoginRequestDto.class);

    } catch (IOException e) {
      throw new RuntimeException(e);
    }

    String email = loginDto.getEmail();
    String password = loginDto.getPassword();

    // 스프링 시큐리티에서 제공하는 인증 수행 로직
    // 사용자 인증을 위한 토큰을 생성
    UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(email, password);

    // 주어진 인증 토큰을 기반으로 인증 수행
    return authenticationManager.authenticate(authToken);
  }

  // 로그인 성공 -> JWT 발급
  @Override
  protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authentication) {

    CustomUserDetails customUserDetails = (CustomUserDetails) authentication.getPrincipal();

    Long userId = customUserDetails.getId();
    String email = customUserDetails.getUsername();

    // 토큰 생성
    String access = jwtUtil.createJwt("access", userId, email, ACCESS_TOKEN_EXPIRATION_MS);
    String refresh = jwtUtil.createJwt("refresh", userId, email, REFRESH_TOKEN_EXPIRATION_MS);

    Refresh refreshEntity = Refresh.createRefreshEntity(email, refresh, ACCESS_TOKEN_EXPIRATION_MS);

    refreshRepository.save(refreshEntity);

    // 응답 설정
    response.setHeader("access", access);
    response.addCookie(createCookie(refresh));
    response.setStatus(HttpStatus.OK.value());
  }

  // 로그인 실패 -> 에러 처리
  @Override
  protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response, AuthenticationException failed) {

    response.setStatus(401);
  }

  private Cookie createCookie(String value) {

    Cookie cookie = new Cookie("refresh", value);
    cookie.setMaxAge(24*60*60);
    // https에서만 쿠키 전송
    //cookie.setSecure(true);
    // 쿠키의 유효 범위
    //cookie.setPath("/");
    cookie.setHttpOnly(true);

    return cookie;
  }
}
