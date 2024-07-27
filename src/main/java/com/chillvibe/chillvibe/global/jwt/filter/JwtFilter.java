package com.chillvibe.chillvibe.global.jwt.filter;

import com.chillvibe.chillvibe.domain.user.entity.User;
import com.chillvibe.chillvibe.global.jwt.dto.CustomUserDetails;
import com.chillvibe.chillvibe.global.jwt.util.JwtUtil;
import io.jsonwebtoken.ExpiredJwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

@RequiredArgsConstructor
public class JwtFilter extends OncePerRequestFilter {

  private final JwtUtil jwtUtil;

  @Override
  protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

    String accessToken = request.getHeader("access");

    if (accessToken == null) {

      filterChain.doFilter(request, response);

      return;
    }

    try {

      jwtUtil.isExpired(accessToken);

    } catch (ExpiredJwtException e) {

      PrintWriter writer = response.getWriter();
      writer.print("accessToken이 만료되었습니다.");

      response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
      return;
    }

    String category = jwtUtil.getCategory(accessToken);

    if (!category.equals("access")) {

      PrintWriter writer = response.getWriter();
      writer.print("accessToken이 유효하지 않습니다.");

      response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
      return;
    }

    Long userId = jwtUtil.getUserId(accessToken);
    String email = jwtUtil.getEmail(accessToken);

    User user = User.builder()
        .id(userId)
        .email(email)
        .build();

    CustomUserDetails customUserDetails = new CustomUserDetails(user);

    Authentication authToken = new UsernamePasswordAuthenticationToken(customUserDetails, null, customUserDetails.getAuthorities());
    SecurityContextHolder.getContext().setAuthentication(authToken);

    filterChain.doFilter(request, response);
  }
}
