package com.chillvibe.chillvibe.domain.user.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Schema(description = "유저의 간단한 정보를 담은 DTO")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserSimpleResponseDto {

  @Schema(description = "사용자 ID")
  private Long id;

  @Schema(description = "사용자 닉네임")
  private String nickname;

  @Schema(description = "사용자 프로필 이미지 URL")
  private String profileUrl;
}
