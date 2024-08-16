package com.chillvibe.chillvibe.domain.user.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

@Schema(description = "회원가입 요청 DTO")
@Getter
public class JoinRequestDto {

  @Schema(description = "사용자 이메일")
  private String email;

  @Schema(description = "사용자 비밀번호")
  private String password;

  @Schema(description = "사용자 닉네임")
  private String nickname;

  @Schema(description = "사용자 소개")
  private String introduction;
}
