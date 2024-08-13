package com.chillvibe.chillvibe.domain.playlist.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

@Schema(description = "검색 후, 플레이리스트에 트랙을 추가할때 사용하는 DTO")
@Getter
@Setter
public class PlaylistTrackRequestDto {
  @Schema(description = "Spotify 트랙 ID")
  private String trackId;
  @Schema(description = "트랙 이름")
  private String name;
  @Schema(description = "아티스트 이름")
  private String artist;
  @Schema(description = "재생 시간")
  private String duration;
  @Schema(description = "미리듣기 URL")
  private String previewUrl;
  @Schema(description = "트랙 썸네일 URL")
  private String thumbnailUrl;
}
