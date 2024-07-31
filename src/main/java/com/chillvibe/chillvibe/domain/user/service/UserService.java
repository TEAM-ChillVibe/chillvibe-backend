package com.chillvibe.chillvibe.domain.user.service;

import com.chillvibe.chillvibe.domain.hashtag.entity.Hashtag;
import com.chillvibe.chillvibe.domain.user.dto.UserInfoResponseDto;
import com.chillvibe.chillvibe.domain.user.entity.User;
import java.util.List;
import java.util.Optional;
import org.springframework.web.multipart.MultipartFile;

public interface UserService {

  void join(String joinDto, MultipartFile multipartFile);
  void update(String userUpdateDto, MultipartFile multipartFile);
  void softDeleteUser();
  void restoreUser();
  UserInfoResponseDto getMyPageInfo();
  UserInfoResponseDto getUserInfo(Long userId);
}