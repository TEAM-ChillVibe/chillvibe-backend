package com.chillvibe.chillvibe.domain.playlist.dto;

import java.time.LocalDateTime;
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
  private String thumbnailUrl;
  private LocalDateTime modifiedAt;
  private int trackCount;
  private List<PlaylistTrackResponseDto> tracks;
}