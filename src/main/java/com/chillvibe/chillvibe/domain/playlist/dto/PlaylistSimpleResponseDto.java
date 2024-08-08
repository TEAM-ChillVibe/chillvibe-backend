package com.chillvibe.chillvibe.domain.playlist.dto;

import com.chillvibe.chillvibe.domain.playlist.entity.Playlist;
import lombok.Getter;

@Getter
public class PlaylistSimpleResponseDto {
  private final Long id;
  private final String title;
  private final int trackCount; // 트랙 수 추가
  private final String imageUrl; // 썸네일 이미지

  public PlaylistSimpleResponseDto(Playlist playlist) {
    this.id = playlist.getId();
    this.title = playlist.getTitle();
    this.trackCount = playlist.getTracks().size();
    this.imageUrl = playlist.getImageUrl();
  }
}
