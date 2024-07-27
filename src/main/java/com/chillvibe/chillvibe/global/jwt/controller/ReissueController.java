package com.chillvibe.chillvibe.global.jwt.controller;

import com.chillvibe.chillvibe.global.jwt.service.ReissueService;
import com.chillvibe.chillvibe.global.jwt.util.JwtUtil;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class ReissueController {

  private final ReissueService reissueService;

  @PostMapping("/reissue")
  public ResponseEntity<String> reissue(HttpServletRequest request, HttpServletResponse response) {

    return reissueService.reissueToken(request, response);
  }
}
