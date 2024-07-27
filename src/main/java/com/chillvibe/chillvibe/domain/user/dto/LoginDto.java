package com.chillvibe.chillvibe.domain.user.dto;

import lombok.Data;
import lombok.Getter;

@Getter
public class LoginDto {

  private String email;
  private String password;
}
