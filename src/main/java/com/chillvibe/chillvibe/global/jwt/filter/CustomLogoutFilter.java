package com.chillvibe.chillvibe.global.jwt.filter;

import com.chillvibe.chillvibe.global.jwt.repository.RefreshRepository;
import com.chillvibe.chillvibe.global.jwt.util.JwtUtil;
import io.jsonwebtoken.ExpiredJwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import lombok.RequiredArgsConstructor;
import org.springframework.web.filter.GenericFilterBean;

@RequiredArgsConstructor
public class CustomLogoutFilter extends GenericFilterBean {

  private static final String LOGOUT_URI = "/logout";
  private static final String LOGOUT_METHOD = "POST";
  private static final String ACCESS_COOKIE_NAME = "access";
  private static final String REFRESH_COOKIE_NAME = "refresh";


  private final JwtUtil jwtUtil;
  private final RefreshRepository refreshRepository;

  @Override
  public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {

    doFilter((HttpServletRequest) request, (HttpServletResponse) response, chain);
  }

  private void doFilter(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws IOException, ServletException {

    // POST/logout 엔트포인트 요청이 왔는지 확인
    String requestUri = request.getRequestURI();
    if (!requestUri.matches("^\\/logout$")) {

      filterChain.doFilter(request, response);
      return;
    }

    String requestMethod = request.getMethod();
    if (!requestMethod.equals("POST")) {

      filterChain.doFilter(request, response);
      return;
    }

    String refresh = null;
    Cookie[] cookies = request.getCookies();

    // refreshToken 가져오기
    for (Cookie cookie : cookies) {
      if (cookie.getName().equals("refresh")) {
        refresh = cookie.getValue();
      }
    }

    // refreshToken이 없다면
    if (refresh == null) {
      response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
      return;
    }

    // refreshToken 만료 확인
    try {
      jwtUtil.isExpired(refresh);
    } catch (ExpiredJwtException e) {
      response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
      return;
    }

    // 토큰이 refreshToken인지 확인
    String category = jwtUtil.getCategory(refresh);
    if (!category.equals("refresh")) {
      response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
      return;
    }

    // DB에 refreshToken이 저장되어 있는지 확인
    Boolean isExist = refreshRepository.existsByToken(refresh);
    if (!isExist) {

      //response status code
      response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
      return;
    }

    // 로그아웃 진행
    // refreshToken DB에서 제거
    refreshRepository.deleteByToken(refresh);

    // refreshToken Cookie에서 제거
    Cookie cookie = new Cookie("refresh", null);
    cookie.setMaxAge(0);
    cookie.setPath("/");

    response.addCookie(cookie);
    response.setStatus(HttpServletResponse.SC_OK);
  }
}
