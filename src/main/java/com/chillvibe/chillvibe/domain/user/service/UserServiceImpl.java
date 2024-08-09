package com.chillvibe.chillvibe.domain.user.service;

import com.chillvibe.chillvibe.domain.hashtag.dto.HashtagResponseDto;
import com.chillvibe.chillvibe.domain.hashtag.service.HashtagService;
import com.chillvibe.chillvibe.domain.user.dto.JoinRequestDto;
import com.chillvibe.chillvibe.domain.user.dto.LoginResponseDto;
import com.chillvibe.chillvibe.domain.user.dto.PasswordUpdateRequestDto;
import com.chillvibe.chillvibe.domain.user.dto.ReAuthResponseDto;
import com.chillvibe.chillvibe.domain.user.dto.UserInfoResponseDto;
import com.chillvibe.chillvibe.domain.user.dto.UserUpdateRequestDto;
import com.chillvibe.chillvibe.domain.user.entity.User;
import com.chillvibe.chillvibe.domain.user.repository.UserRepository;
import com.chillvibe.chillvibe.global.error.ErrorCode;
import com.chillvibe.chillvibe.global.error.exception.ApiException;
import com.chillvibe.chillvibe.global.jwt.repository.RefreshRepository;
import com.chillvibe.chillvibe.global.jwt.util.JwtUtil;
import com.chillvibe.chillvibe.global.jwt.util.UserUtil;
import com.chillvibe.chillvibe.global.s3.service.S3Uploader;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.ExpiredJwtException;
import io.micrometer.common.util.StringUtils;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.transaction.Transactional;
import java.io.IOException;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

  private final UserRepository userRepository;
  private final BCryptPasswordEncoder bCryptPasswordEncoder;
  private final S3Uploader s3Uploader;
  private final ObjectMapper objectMapper;
  private final UserUtil userUtil;
  private final JwtUtil jwtUtil;
  private final RefreshRepository refreshRepository;
  private final HttpServletRequest request;
  private final HttpServletResponse response;
  private final HashtagService hashtagService;

  public void join(String joinDto, MultipartFile multipartFile) {

    JoinRequestDto parsedJoinDto;

    try {
      // JSON 문자열을 JoinDto 객체로 변환
      parsedJoinDto = objectMapper.readValue(joinDto, JoinRequestDto.class);
    } catch (IOException e) {
      throw new ApiException(ErrorCode.INVALID_TYPE_VALUE);
    }

    // 이메일 가져와서 이미 존재하는 이메일인지 확인
    String email = parsedJoinDto.getEmail();
    String password = parsedJoinDto.getPassword();

    Boolean isExist = userRepository.existsByEmail(email);

    // 이미 존재한다면
    if (isExist) {
      throw new ApiException(ErrorCode.DUPLICATE_EMAIL);
    }

    // 이미지를 s3에 업로드하고 url 가져오기
    String imageUrl = null;
    if (multipartFile != null && !multipartFile.isEmpty()) {
      try {
        imageUrl = s3Uploader.upload(multipartFile, "profile-images");
        // 회원가입 로직 처리 (DB 저장 등)
        ResponseEntity.status(HttpStatus.CREATED)
            .body("User registered successfully with profile image URL: " + imageUrl);
      } catch (IOException e) {
        throw new ApiException("Error uploading profile image", ErrorCode.INTERNAL_SERVER_ERROR);
      }
    }

    User newUser = User.builder()
        .email(email)
        .password(bCryptPasswordEncoder.encode(password))
        .nickname(parsedJoinDto.getNickname())
        .profileUrl(imageUrl)
        .introduction(parsedJoinDto.getIntroduction())
        .build();

    userRepository.save(newUser);
  }

  @Transactional
  public void update(String userUpdateDto, MultipartFile multipartFile) {

    Long userId = userUtil.getAuthenticatedUserId();

    User user = userRepository.findById(userId)
        .orElseThrow(() -> new ApiException(ErrorCode.USER_NOT_FOUND));

    UserUpdateRequestDto parsedUserUpdateDto;

    try {
      parsedUserUpdateDto = objectMapper.readValue(userUpdateDto, UserUpdateRequestDto.class);
    } catch (IOException e) {
      throw new ApiException(ErrorCode.INVALID_TYPE_VALUE);
    }

    // imageUrl 기존 url로 초기화
    String imageUrl = user.getProfileUrl();

    // 만약 multiparFile에 파일이 들어왔다면
    if (multipartFile != null && !multipartFile.isEmpty()) {
      // 기존의 profileUrl 삭제
      s3Uploader.deleteFile(user.getProfileUrl());

      // 새로운 이미지를 업로드하고 URL을 설정
      try {
        imageUrl = s3Uploader.upload(multipartFile, "profile-images");
      } catch (IOException e) {
        throw new ApiException("Error uploading profile image", ErrorCode.INTERNAL_SERVER_ERROR);
      }
    }

    hashtagService.updateHashtagsOfUser(userId, parsedUserUpdateDto.getHashtagIds());

    // 수정된 정보와 imageUrl 세팅
    User updatedUser = user.updateUser(parsedUserUpdateDto, imageUrl);

    userRepository.save(updatedUser);
  }

  @Transactional
  public void updatePassword(PasswordUpdateRequestDto passwordUpdateRequestDto) {
    Long userId = userUtil.getAuthenticatedUserId();

    User user = userRepository.findById(userId)
            .orElseThrow(() -> new ApiException(ErrorCode.USER_NOT_FOUND));

    String oldPassword = passwordUpdateRequestDto.getOldPassword();
    String newPassword = passwordUpdateRequestDto.getNewPassword();
    String confirmPassword = passwordUpdateRequestDto.getConfirmPassword();

    // 비밀번호 값이 모두 존재하는지 확인 (null이 아니고 공백이 아닌지 확인)
    if (StringUtils.isBlank(oldPassword) || StringUtils.isBlank(newPassword) || StringUtils.isBlank(confirmPassword)) {
      throw new ApiException(ErrorCode.INVALID_INPUT_VALUE); // INVALID_INPUT_VALUE 오류 코드 추가
    }

    // 기존 비밀번호 확인
    if (!bCryptPasswordEncoder.matches(oldPassword, user.getPassword())) {
      throw new ApiException(ErrorCode.INVALID_PASSWORD);
    }

    // 새 비밀번호와 확인 비밀번호가 일치하는지 확인
    if (!newPassword.equals(confirmPassword)) {
      throw new ApiException(ErrorCode.PASSWORDS_DO_NOT_MATCH);
    }

    // 비밀번호 업데이트
    user.updatePassword(newPassword, bCryptPasswordEncoder);

    // 사용자 정보 저장
    userRepository.save(user);
  }

  @Transactional
  public void softDeleteUser() {
    Long userId = userUtil.getAuthenticatedUserId();

    User user = userRepository.findById(userId)
        .orElseThrow(() -> new ApiException(ErrorCode.USER_NOT_FOUND));

    userRepository.delete(user);
    performLogout(request, response);
  }

  @Transactional
  public void restoreUser() {
    Long userId = userUtil.getAuthenticatedUserId();

    userRepository.findById(userId)
        .orElseThrow(() -> new ApiException(ErrorCode.USER_NOT_FOUND));

    userRepository.restore(userId);
  }

  public UserInfoResponseDto getMyPageInfo() {
    Long userId = userUtil.getAuthenticatedUserId();

    User user = userRepository.findById(userId)
        .orElseThrow(() -> new ApiException(ErrorCode.USER_NOT_FOUND));

    List<HashtagResponseDto> hashtags = hashtagService.getHashtagsOfUser(userId);

//    List<UserHashtag> userHashtag = userHashtagRepository.findByUserId(userId);
//
//    List<Hashtag> hashtags = userHashtag.stream()
//        .map(UserHashtag::getHashtag)
//        .filter(Objects::nonNull)
//        .toList();

    return new UserInfoResponseDto(user, hashtags);
  }

  public UserInfoResponseDto getUserInfo(Long userId) {

    User user = userRepository.findById(userId)
        .orElseThrow(() -> new ApiException(ErrorCode.USER_NOT_FOUND));

//    List<UserHashtag> userHashtag = userHashtagRepository.findByUserId(userId);
//
//    List<Hashtag> hashtags = userHashtag.stream()
//        .map(UserHashtag::getHashtag)
//        .filter(Objects::nonNull)
//        .toList();

    List<HashtagResponseDto> hashtags = hashtagService.getHashtagsOfUser(userId);

    return new UserInfoResponseDto(user, hashtags);
  }

  public User getUserById(Long userId) {
    return userRepository.findById(userId)
        .orElseThrow(() -> new ApiException(ErrorCode.USER_NOT_FOUND));
  }

  public ReAuthResponseDto doReAuth() {
    Long userId = userUtil.getAuthenticatedUserId();

    User user = userRepository.findById(userId)
        .orElseThrow(() -> new ApiException(ErrorCode.USER_NOT_FOUND));

    return new ReAuthResponseDto(user);
  }

  private void performLogout(HttpServletRequest request, HttpServletResponse response) {
    String refreshToken = getRefreshTokenFromCookies(request);

    if (refreshToken != null) {
      // refreshToken 만료 확인
      try {
        jwtUtil.isExpired(refreshToken);
      } catch (ExpiredJwtException e) {
        response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        return;
      }

      // refreshToken이 유효한지 확인
      String category = jwtUtil.getCategory(refreshToken);
      if (!"refresh".equals(category)) {
        response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        return;
      }

      // DB에서 refreshToken 삭제
      boolean exists = refreshRepository.existsByToken(refreshToken);
      if (exists) {
        refreshRepository.deleteByToken(refreshToken);
      }

      // 쿠키에서 refreshToken 제거
      Cookie cookie = new Cookie("refresh", null);
      cookie.setMaxAge(0);
      cookie.setPath("/");
      response.addCookie(cookie);

      response.setStatus(HttpServletResponse.SC_OK);
    } else {
      response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
    }
  }

  private String getRefreshTokenFromCookies(HttpServletRequest request) {
    Cookie[] cookies = request.getCookies();
    if (cookies != null) {
      for (Cookie cookie : cookies) {
        if ("refresh".equals(cookie.getName())) {
          return cookie.getValue();
        }
      }
    }
    return null;
  }

  // 문자열이 null이거나 빈 문자열인지 확인하는 메소드
  private boolean isBlank(String str) {
    return str == null || str.trim().isEmpty();
  }
}
