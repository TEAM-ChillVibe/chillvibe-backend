package com.chillvibe.chillvibe.domain.user.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import java.util.List;
import lombok.Getter;

@Schema(description = "프로필 수정 요청 DTO")
@Getter
public class UserUpdateRequestDto {

  @Schema(description = "사용자 닉네임")
  private String nickname;

  @Schema(description = "사용자 소개")
  private String introduction;

  @Schema(description = "사용자 선호 해시태그 정보")
  private List<Long> hashtagIds;

  @Schema(description = "프로필 공개 여부")
  @JsonProperty("isPublic")
  private boolean isPublic;
}
