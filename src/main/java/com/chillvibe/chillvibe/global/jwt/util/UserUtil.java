package com.chillvibe.chillvibe.global.jwt.util;

import com.chillvibe.chillvibe.global.jwt.dto.CustomUserDetails;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserUtil {

  private final JwtUtil jwtUtil;

  public Long getAuthenticatedUserId() {

    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

    if (authentication != null) {

      Object principal = authentication.getPrincipal();

      if (principal instanceof CustomUserDetails userDetails) {

        return userDetails.getId();
      }
    }

    return null;
  }

  public String getAuthenticatedEmail() {

    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

    if (authentication != null) {

      Object principal = authentication.getPrincipal();

      if (principal instanceof CustomUserDetails userDetails) {

        return userDetails.getUsername();
      }
    }

    return null;
  }
}
