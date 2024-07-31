package com.chillvibe.chillvibe.domain.playlist.dto;

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

}
