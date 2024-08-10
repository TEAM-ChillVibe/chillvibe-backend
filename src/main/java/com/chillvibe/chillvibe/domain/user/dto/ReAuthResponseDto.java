package com.chillvibe.chillvibe.domain.user.dto;

import com.chillvibe.chillvibe.domain.user.entity.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ReAuthResponseDto {

  private Long userId;
  private String email;
  private String nickname;
  private String introduction;
  private String profileUrl;
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
