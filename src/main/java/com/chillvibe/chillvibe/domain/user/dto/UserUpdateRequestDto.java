package com.chillvibe.chillvibe.domain.user.dto;

import lombok.Getter;

@Getter
public class UserUpdateRequestDto {

  private String nickname;
  private String introduction;
  private boolean isPublic;
}
