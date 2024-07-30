package com.chillvibe.chillvibe.domain.user.service;

import com.chillvibe.chillvibe.domain.user.dto.JoinRequestDto;
import com.chillvibe.chillvibe.domain.user.dto.UserUpdateRequestDto;
import com.chillvibe.chillvibe.domain.user.entity.User;
import com.chillvibe.chillvibe.domain.user.exception.DuplicateEmailException;
import com.chillvibe.chillvibe.domain.user.exception.UserNotFoundException;
import com.chillvibe.chillvibe.domain.user.repository.UserRepository;
import com.chillvibe.chillvibe.global.jwt.util.UserUtil;
import com.chillvibe.chillvibe.global.s3.service.S3Uploader;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
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

  public void join(String joinDto, MultipartFile multipartFile){

    JoinRequestDto parsedJoinDto = new JoinRequestDto();
    try {
      // JSON 문자열을 JoinDto 객체로 변환
      parsedJoinDto = objectMapper.readValue(joinDto, JoinRequestDto.class);
    } catch (IOException e) {
      ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid JSON format: " + e.getMessage());
    }

    // 이메일 가져와서 이미 존재하는 이메일인지 확인
    String email = parsedJoinDto.getEmail();
    String password = parsedJoinDto.getPassword();

    Boolean isExist = userRepository.existsByEmail(email);

    // 이미 존재한다면
    if(isExist){
      throw new DuplicateEmailException();
    }

    // 이미지를 s3에 업로드하고 url 가져오기
    String imageUrl = null;
    if (multipartFile != null && !multipartFile.isEmpty()) {
      try {
        imageUrl = s3Uploader.upload(multipartFile, "profile-images");
        // 회원가입 로직 처리 (DB 저장 등)
        ResponseEntity.status(HttpStatus.CREATED).body("User registered successfully with profile image URL: " + imageUrl);
      } catch (IOException e) {
        ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error uploading profile image: " + e.getMessage());
        return;
      }
    }

    User newUser = User.builder()
        .email(email)
        .password(bCryptPasswordEncoder.encode(password))
        .nickname(parsedJoinDto.getNickname())
        .profileUrl(imageUrl)
        .introduction(parsedJoinDto.getIntroduction())
        .isPublic(Boolean.TRUE)
        .isDelete(Boolean.FALSE)
        .build();

    userRepository.save(newUser);
  }

  public void update(String userUpdateDto, MultipartFile multipartFile) {

    Long userId = userUtil.getAuthenticatedUserId();

    System.out.println(userId);

    UserUpdateRequestDto parsedUserUpdateDto = new UserUpdateRequestDto();
    try {
      parsedUserUpdateDto = objectMapper.readValue(userUpdateDto, UserUpdateRequestDto.class);
    } catch (IOException e) {
      ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid JSON format: " + e.getMessage());
    }

    // 인증된 유저 객체 가져오기
    User user = userRepository.findById(userId)
        .orElseThrow(UserNotFoundException::new);

    // imageUrl 기존 url로 초기화
    String imageUrl = user.getProfileUrl();

    // 만약 multiparFile에 파일이 들어왔다면
    if(multipartFile != null && !multipartFile.isEmpty()){
      // 기존의 profileUrl 삭제
      s3Uploader.deleteFile(user.getProfileUrl());

      // 새로운 이미지를 업로드하고 URL을 설정
      try {
        imageUrl = s3Uploader.upload(multipartFile, "profile-images");
      } catch (IOException e) {
        ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error uploading profile image: " + e.getMessage());
      }
    }

    // 수정된 정보와 imageUrl 세팅
    User updatedUser = user.updateUser(parsedUserUpdateDto, imageUrl);

    userRepository.save(updatedUser);
  }
}
