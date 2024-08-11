package com.chillvibe.chillvibe.domain.user.dto;

import com.chillvibe.chillvibe.domain.hashtag.dto.HashtagResponseDto;
import com.chillvibe.chillvibe.domain.user.entity.User;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
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
}
