package com.chillvibe.chillvibe.domain.user.service;

import com.chillvibe.chillvibe.domain.user.dto.JoinDto;
import com.chillvibe.chillvibe.domain.user.entity.User;
import com.chillvibe.chillvibe.domain.user.exception.DuplicateEmailException;
import com.chillvibe.chillvibe.domain.user.repository.UserRepository;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

public interface UserService {

  void join(JoinDto joinDto);

}
