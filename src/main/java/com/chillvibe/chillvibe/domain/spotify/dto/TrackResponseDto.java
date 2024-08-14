package com.chillvibe.chillvibe.domain.spotify.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Value;

@Schema(description = "Spotify 트랙 DTO")
@Value
public class TrackResponseDto {
  @Schema(description = "Spotify Track ID")
  String id;
  @Schema(description = "트랙 이름")
  String name;
  @Schema(description = "아티스트 이름")
  String artist;
  @Schema(description = "트랙 썸네일 URL")
  String thumbnailUrl;
  @Schema(description = "트랙 미리듣기 URL")
  String previewUrl;
  @Schema(description = "트랙 재생 시간 ex) 03:31")
  String duration;
}
