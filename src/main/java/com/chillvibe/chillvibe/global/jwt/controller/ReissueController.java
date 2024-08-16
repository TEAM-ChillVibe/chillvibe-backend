package com.chillvibe.chillvibe.global.jwt.controller;

import com.chillvibe.chillvibe.global.jwt.service.ReissueService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
@Tag(name = "Reissue", description = "토큰 재발급")
public class ReissueController {

  private final ReissueService reissueService;


  @Operation(summary = "토큰 재발급", description = "토큰을 재발급 하는데 사용하는 API")
  @PostMapping("/reissue")
  public ResponseEntity<String> reissue(HttpServletRequest request, HttpServletResponse response) {

    return reissueService.reissueToken(request, response);
  }
}
