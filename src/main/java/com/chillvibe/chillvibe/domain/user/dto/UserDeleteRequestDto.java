package com.chillvibe.chillvibe.domain.user.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

@Schema(description = "탈퇴 요청 DTO")
@Getter
public class UserDeleteRequestDto {

  @Schema(description = "사용자 비밀번호")
  String password;
}
