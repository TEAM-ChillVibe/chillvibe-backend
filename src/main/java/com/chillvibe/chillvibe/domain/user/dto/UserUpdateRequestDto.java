package com.chillvibe.chillvibe.domain.user.dto;

import lombok.Getter;

@Getter
public class UserUpdateRequestDto {

  private String nickname;
  private String introduction;
  private String oldPassword;
  private String newPassword;
  private String confirmNewPassword;
  private boolean isPublic;
}
