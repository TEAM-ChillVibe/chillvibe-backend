package com.chillvibe.chillvibe.domain.user.dto;

import com.chillvibe.chillvibe.domain.user.entity.User;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;


@Schema(description = "재인증 응답 DTO")
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ReAuthResponseDto {

  @Schema(description = "사용자 ID")
  private Long userId;
  @Schema(description = "사용자 이메일")
  private String email;
  @Schema(description = "사용자 닉네임")
  private String nickname;
  @Schema(description = "사용자 소개")
  private String introduction;
  @Schema(description = "사용자 프로필 이미지 URL")
  private String profileUrl;
  @Schema(description = "프로필 공개 여부")
  private boolean isPublic;

  public ReAuthResponseDto(User user) {
    this.userId = user.getId();
    this.email = user.getEmail();
    this.nickname = user.getNickname();
    this.profileUrl = user.getProfileUrl();
    this.introduction = user.getIntroduction();
    this.isPublic = user.isPublic();
  }
}
