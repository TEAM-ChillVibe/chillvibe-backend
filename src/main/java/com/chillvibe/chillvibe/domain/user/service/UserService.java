package com.chillvibe.chillvibe.domain.user.service;

import org.springframework.web.multipart.MultipartFile;

public interface UserService {

  void join(String joinDto, MultipartFile multipartFile);
  void update(String userUpdateDto, MultipartFile multipartFile);
}