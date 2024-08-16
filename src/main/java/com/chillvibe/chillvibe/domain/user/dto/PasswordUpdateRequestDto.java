package com.chillvibe.chillvibe.domain.user.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

@Schema(description = "비밀번호 변경 요청 DTO")
@Getter
public class PasswordUpdateRequestDto {

    @Schema(description = "이전 비밀번호")
    private String oldPassword;

    @Schema(description = "새 비밀번호")
    private String newPassword;

    @Schema(description = "새 비밀번호 확인")
    private String confirmPassword;
}
