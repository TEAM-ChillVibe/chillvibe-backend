package com.chillvibe.chillvibe.domain.playlist.dto;

import com.chillvibe.chillvibe.domain.playlist.entity.Playlist;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class PlaylistSimpleResponseDto {
  private Long id;
  private String title;
  private int trackCount;
  private String thumbnailUrl;
}
