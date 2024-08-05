package com.chillvibe.chillvibe.domain.user.dto;

import com.chillvibe.chillvibe.domain.hashtag.dto.HashtagResponseDto;
import com.chillvibe.chillvibe.domain.user.entity.User;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class UserInfoResponseDto {

  private Long userId;
  private String email;
  private String nickname;
  private String profileUrl;
  private String introduction;
  private boolean isPublic;
  private List<HashtagResponseDto> hashtags;

  public UserInfoResponseDto(User user, List<HashtagResponseDto> hashtags) {
    this.userId = user.getId();
    this.email = user.getEmail();
    this.nickname = user.getNickname();
    this.profileUrl = user.getProfileUrl();
    this.introduction = user.getIntroduction();
    this.isPublic = user.isPublic();
    this.hashtags = hashtags;
  }
}
