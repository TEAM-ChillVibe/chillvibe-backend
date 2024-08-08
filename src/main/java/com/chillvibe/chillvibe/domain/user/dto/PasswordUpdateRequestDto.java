package com.chillvibe.chillvibe.domain.user.dto;

import lombok.Getter;

@Getter
public class PasswordUpdateRequestDto {
    private String oldPassword;
    private String newPassword;
    private String confirmPassword;
}
