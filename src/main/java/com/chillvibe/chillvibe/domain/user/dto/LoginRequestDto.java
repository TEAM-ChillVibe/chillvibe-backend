package com.chillvibe.chillvibe.domain.user.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

@Schema(description = "로그인 요청 DTO")
@Getter
public class LoginRequestDto {

  @Schema(description = "사용자 이메일")
  private String email;

  @Schema(description = "사용자 비밀번호")
  private String password;
}
