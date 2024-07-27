package com.chillvibe.chillvibe.domain.user.service;

import com.chillvibe.chillvibe.domain.user.dto.JoinDto;
import com.chillvibe.chillvibe.domain.user.entity.User;
import com.chillvibe.chillvibe.domain.user.exception.DuplicateEmailException;
import com.chillvibe.chillvibe.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

  private final UserRepository userRepository;
  private final BCryptPasswordEncoder bCryptPasswordEncoder;

  public void join(JoinDto joinDto){

    // 이메일 가져와서 이미 존재하는 이메일인지 확인
    String email = joinDto.getEmail();
    String password = joinDto.getPassword();
    System.out.println(email);
    Boolean isExist = userRepository.existsByEmail(email);

    // 이미 존재한다면
    if(isExist){
      throw new DuplicateEmailException();
    }

    User newUser = User.builder()
        .email(email)
        .password(bCryptPasswordEncoder.encode(password))
        .nickname(joinDto.getNickname())
        .profileUrl(joinDto.getProfileUrl())
        .introduction(joinDto.getIntroduction())
        .isPublic(true)
        .isDelete(false)
        .build();

    userRepository.save(newUser);
  }
}
