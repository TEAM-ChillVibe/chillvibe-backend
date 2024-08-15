package com.chillvibe.chillvibe.domain.user.dto;

import com.chillvibe.chillvibe.domain.hashtag.dto.HashtagResponseDto;
import io.swagger.v3.oas.annotations.media.Schema;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Schema(description = "유저의 정보가 담겨있는 DTO")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserInfoResponseDto {

  @Schema(description = "유저 ID")
  private Long userId;

  @Schema(description = "유저 이메일")
  private String email;

  @Schema(description = "유저 닉네임")
  private String nickname;

  @Schema(description = "유저 프로필 이미지")
  private String profileUrl;

  @Schema(description = "유저 소개 글")
  private String introduction;

  @Schema(description = "유저 페이지 공개 여부")
  private boolean isPublic;

  @Schema(description = "유저 선호 해시태그 정보")
  private List<HashtagResponseDto> hashtags;
}
