package com.chillvibe.chillvibe.domain.user.controller;

import com.chillvibe.chillvibe.domain.user.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@Slf4j
@RestController
@RequiredArgsConstructor
public class UserController {

  private final UserService userService;

  @PostMapping("/signup")
  public ResponseEntity<String> join(@RequestPart(value = "joinDto") String joinDto, @RequestPart(value = "profileImg") MultipartFile multipartFile) {

    userService.join(joinDto, multipartFile);

    return ResponseEntity.status(HttpStatus.CREATED).body("회원가입 완료");
  }

  @PutMapping("/users")
  public ResponseEntity<String> update(@RequestPart(value = "userUpdateDto") String userUpdateDto, @RequestPart(value = "profileImg") MultipartFile multipartFile) {

    userService.update(userUpdateDto, multipartFile);

    return ResponseEntity.status(HttpStatus.OK).body("회원 정보 수정 완료");
  }
}