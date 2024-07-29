package com.chillvibe.chillvibe.domain.user.dto;

import lombok.Getter;

@Getter
public class JoinRequestDto {

  private String email;
  private String password;
  private String nickname;
  private String introduction;
}
