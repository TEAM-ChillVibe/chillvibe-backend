package com.chillvibe.chillvibe.domain.user.dto;

import lombok.Data;
import lombok.Getter;

@Getter
public class JoinDto {

  private String email;
  private String password;
  private String nickname;
  private String profileUrl;
  private String introduction;
}
