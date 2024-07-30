package com.chillvibe.chillvibe.global.jwt.service;

import com.chillvibe.chillvibe.global.jwt.entity.Refresh;
import com.chillvibe.chillvibe.global.jwt.repository.RefreshRepository;
import com.chillvibe.chillvibe.global.jwt.util.JwtUtil;
import io.jsonwebtoken.ExpiredJwtException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ReissueService {

  private static final long ACCESS_TOKEN_EXPIRATION_MS = 60*60*60*10L;
  private static final long REFRESH_TOKEN_EXPIRATION_MS = 60*60*60*24L;

  private final JwtUtil jwtUtil;
  private final RefreshRepository refreshRepository;

  public ResponseEntity<String> reissueToken(HttpServletRequest request, HttpServletResponse response) {

    String refresh = null;

    // 쿠키에서 refreshToken 가져오기
    Cookie[] cookies = request.getCookies();
    for (Cookie cookie : cookies) {
      if (cookie.getName().equals("refresh")) {
        refresh = cookie.getValue();
      }
    }

    // refreshToken이 없다면
    if (refresh == null) {
      return new ResponseEntity<>("refreshToken이 없습니다.", HttpStatus.BAD_REQUEST);
    }

    // refreshToken 만료 확인
    try {
      jwtUtil.isExpired(refresh);
    } catch (ExpiredJwtException e) {
      return new ResponseEntity<>("refreshToken이 만료되었습니다.", HttpStatus.BAD_REQUEST);
    }

    String category = jwtUtil.getCategory(refresh);

    // 가져온 토큰이 refreshToken인지 확인
    if (!category.equals("refresh")) {
      return new ResponseEntity<>("refreshToken이 유효하지 않습니다.", HttpStatus.BAD_REQUEST);
    }

    // DB에 해당 refreshToken이 저장되어 있는지 확인
    Boolean isExist = refreshRepository.existsByToken(refresh);
    if(!isExist) {
      return new ResponseEntity<>("DB에 refreshToken이 존재하지 않습니다.", HttpStatus.BAD_REQUEST);
    }

    // 모든 조건을 만족하면 토큰 재생성
    Long userId = jwtUtil.getUserId(refresh);
    String email = jwtUtil.getEmail(refresh);

    String newAccess = jwtUtil.createJwt("access", userId, email, ACCESS_TOKEN_EXPIRATION_MS);
    String newRefresh = jwtUtil.createJwt("refresh", userId, email, REFRESH_TOKEN_EXPIRATION_MS);

    // DB에 있는 기존의 refreshToken 삭제 후 새로 발급한 refreshToken 저장
    refreshRepository.deleteByToken(refresh);

    Refresh refreshEntity = Refresh.createRefreshEntity(email, newRefresh, ACCESS_TOKEN_EXPIRATION_MS);
    refreshRepository.save(refreshEntity);

    response.setHeader("access", newAccess);
    response.addCookie(createCookie("refresh", newRefresh));

    return new ResponseEntity<>(HttpStatus.OK);
  }

  private Cookie createCookie(String key, String value) {

    Cookie cookie = new Cookie(key, value);
    cookie.setMaxAge(24*60*60);
    //cookie.setSecure(true);
    //cookie.setPath("/");
    cookie.setHttpOnly(true);

    return cookie;
  }
}
