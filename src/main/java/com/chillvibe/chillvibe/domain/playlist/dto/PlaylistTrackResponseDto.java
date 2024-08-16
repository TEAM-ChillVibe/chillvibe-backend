package com.chillvibe.chillvibe.domain.playlist.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

@Schema(description = "담은 플레이리스트 트랙들을 보여줄 때 사용하는 DTO \n\n" +
    "TrackListListItem, TrackListEditItem 컴포넌트에 필요한 값들 입니다.")
@Getter
@Setter
public class PlaylistTrackResponseDto {

  @Schema(description = "플레이리스트 ID")
  private Long id;

  @Schema(description = "Spotify TrackId")
  private String trackId;

  @Schema(description = "트랙 이름")
  private String name;

  @Schema(description = "아티스트")
  private String artist;

  @Schema(description = "재생 시간")
  private String duration;

  @Schema(description = "미리듣기 URL")
  private String previewUrl;

  @Schema(description = "트랙 썸네일 URL")
  private String thumbnailUrl;
}
