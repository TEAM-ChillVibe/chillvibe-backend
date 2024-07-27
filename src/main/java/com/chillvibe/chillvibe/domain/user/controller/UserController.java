package com.chillvibe.chillvibe.domain.user.controller;

import com.chillvibe.chillvibe.domain.user.dto.JoinDto;
import com.chillvibe.chillvibe.domain.user.service.UserService;
import com.chillvibe.chillvibe.global.jwt.util.UserUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class UserController {

  private final UserService userService;

  @PostMapping("/signup")
  public ResponseEntity<String> join(@RequestBody JoinDto joinDto){

    userService.join(joinDto);

    return ResponseEntity.status(HttpStatus.CREATED).body("회원가입 완료");
  }
}
