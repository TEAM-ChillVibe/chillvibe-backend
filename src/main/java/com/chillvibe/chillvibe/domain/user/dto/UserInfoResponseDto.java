package com.chillvibe.chillvibe.domain.user.dto;

import com.chillvibe.chillvibe.domain.hashtag.dto.HashtagResponseDto;
import com.chillvibe.chillvibe.domain.hashtag.entity.Hashtag;
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
  private List<HashtagResponseDto> tagLists;

  public UserInfoResponseDto(User user, List<Hashtag> hashtags) {
    this.userId = user.getId();
    this.email = user.getEmail();
    this.nickname = user.getNickname();
    this.profileUrl = user.getProfileUrl();
    this.introduction = user.getIntroduction();
    this.isPublic = user.isPublic();
    this.tagLists = hashtags.stream()
        .map(hashtag -> new HashtagResponseDto(hashtag.getId(), hashtag.getName(),
            hashtag.getTotalLikes())).toList();
  }
}
