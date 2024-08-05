package com.chillvibe.chillvibe.domain.playlist.dto;

import java.util.List;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class PlaylistResponseDto {

  private Long id;
  private String title;
  private String imageUrl;
  private int trackCount;
  private List<PlaylistTrackResponseDto> tracks;
  private List<String> thumbnailUrls; // 대표이미지용 썸네일 4개 리스트
}
