package com.chillvibe.chillvibe.domain.user.controller;

import com.chillvibe.chillvibe.domain.user.dto.PasswordUpdateRequestDto;
import com.chillvibe.chillvibe.domain.user.dto.ReAuthResponseDto;
import com.chillvibe.chillvibe.domain.user.dto.UserDeleteRequestDto;
import com.chillvibe.chillvibe.domain.user.dto.UserInfoResponseDto;
import com.chillvibe.chillvibe.domain.user.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
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
@Tag(name = "User", description = "유저 API")
public class UserController {

  private final UserService userService;

  @Operation(summary = "회원가입", description = "회원가입을 하는데 사용하는 API")
  @PostMapping("/signup")
  public ResponseEntity<String> join(@RequestPart(value = "joinDto") String joinDto, @RequestPart(value = "profileImg") MultipartFile multipartFile) {

    userService.join(joinDto, multipartFile);

    return ResponseEntity.status(HttpStatus.CREATED).body("회원가입 완료");
  }

  @Operation(summary = "회원정보 수정", description = "회원정보를 수정하는데 사용하는 API")
  @PutMapping("/users")
  public ResponseEntity<String> update(@RequestPart(value = "userUpdateDto") String userUpdateDto, @RequestPart(value = "profileImg", required = false) MultipartFile multipartFile) {

    userService.update(userUpdateDto, multipartFile);

    return ResponseEntity.status(HttpStatus.OK).body("회원 정보 수정 완료");
  }

  @Operation(summary = "비밀번호 수정", description = "비밀번호를 수정하는데 사용하는 API")
  @PutMapping("/password")
  public ResponseEntity<String> updatePassword(@RequestBody PasswordUpdateRequestDto passwordUpdateRequestDto) {

    userService.updatePassword(passwordUpdateRequestDto);

    return ResponseEntity.status(HttpStatus.OK).body("비밀번호 변경 완료");
  }

  @Operation(summary = "회원 탈퇴 (Soft Delete)", description = "회원탈퇴하는데 사용되는 API")
  @DeleteMapping("/users/delete")
  public ResponseEntity<String> softDeleteUser(@RequestBody UserDeleteRequestDto userDeleteRequestDto) {
    userService.softDeleteUser(userDeleteRequestDto);
    return ResponseEntity.status(HttpStatus.OK).body("회원 탈퇴 완료");
  }

  @PatchMapping("/users/restore")
  public ResponseEntity<String> restoreUser() {
    userService.restoreUser();
    return ResponseEntity.status(HttpStatus.OK).body("회원 복구 완료");
  }

  @Operation(summary = "마이 페이지 유저 정보 조회", description = "마이 페이지의 유저 정보를 조회하는데 사용되는 API")
  @GetMapping("/mypage")
  public ResponseEntity<UserInfoResponseDto> getMyInfo() {
    UserInfoResponseDto userInfo = userService.getMyPageInfo();
    return ResponseEntity.ok(userInfo);
  }

  @Operation(summary = "유저 페이지 정보 조회", description = "유저 페이지의 유저 정보를 조회하는데 사용되는 API")
  @GetMapping("/userpage")
  public ResponseEntity<UserInfoResponseDto> getUserInfo(@RequestParam Long userId) {
    UserInfoResponseDto userInfo = userService.getUserInfo(userId);
    return ResponseEntity.ok(userInfo);
  }

  @Operation(summary = "재인증", description = "새로고침 될 때마다 인증하여 Store에 세팅하는데 사용하는 API")
  @GetMapping("/reauth")
  public ResponseEntity<ReAuthResponseDto> doReAuth() {
    ReAuthResponseDto reAuthResponseDto = userService.doReAuth();
    return ResponseEntity.ok(reAuthResponseDto);
  }
}