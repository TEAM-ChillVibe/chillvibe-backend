package com.chillvibe.chillvibe.domain.user.controller;

import com.chillvibe.chillvibe.domain.hashtag.entity.Hashtag;
import com.chillvibe.chillvibe.domain.user.dto.PasswordUpdateRequestDto;
import com.chillvibe.chillvibe.domain.user.dto.ReAuthResponseDto;
import com.chillvibe.chillvibe.domain.user.dto.UserInfoResponseDto;
import com.chillvibe.chillvibe.domain.user.entity.User;
import com.chillvibe.chillvibe.domain.user.service.UserService;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@Slf4j
@RestController
@RequestMapping("/api")
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

  @PutMapping("/password")
  public ResponseEntity<String> updatePassword(@RequestBody PasswordUpdateRequestDto passwordUpdateRequestDto) {

    userService.updatePassword(passwordUpdateRequestDto);

    return ResponseEntity.status(HttpStatus.OK).body("비밀번호 변경 완료");
  }

  @PatchMapping("/users/delete")
  public ResponseEntity<String> softDeleteUser() {
    userService.softDeleteUser();
    return ResponseEntity.status(HttpStatus.OK).body("회원 탈퇴 완료");
  }

  @PatchMapping("/users/restore")
  public ResponseEntity<String> restoreUser() {
    userService.restoreUser();
    return ResponseEntity.status(HttpStatus.OK).body("회원 복구 완료");
  }

  @GetMapping("/mypage")
  public ResponseEntity<UserInfoResponseDto> getMyInfo() {
    UserInfoResponseDto userInfo = userService.getMyPageInfo();
    return ResponseEntity.ok(userInfo);
  }

  @GetMapping("/userpage")
  public ResponseEntity<UserInfoResponseDto> getUserInfo(@RequestParam Long userId) {
    UserInfoResponseDto userInfo = userService.getUserInfo(userId);
    return ResponseEntity.ok(userInfo);
  }

  @GetMapping("/reauth")
  public ResponseEntity<ReAuthResponseDto> doReAuth() {
    ReAuthResponseDto reAuthResponseDto = userService.doReAuth();
    return ResponseEntity.ok(reAuthResponseDto);
  }
}