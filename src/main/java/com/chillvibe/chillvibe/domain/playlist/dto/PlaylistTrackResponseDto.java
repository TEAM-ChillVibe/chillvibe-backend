package com.chillvibe.chillvibe.domain.playlist.dto;

import lombok.Getter;
import lombok.Setter;

// 담은 플레이리스트 트랙을 보여줄 때 사용합니다.
@Getter
@Setter
public class PlaylistTrackResponseDto {
  private Long id;
  private String trackId;
  private String name;
  private String artist;
  private String duration;
  private String previewUrl;
  private String thumbnailUrl;
}
