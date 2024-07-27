package com.chillvibe.chillvibe.domain.spotify.dto;

import lombok.Getter;

@Getter
public class TrackDto {

  private final String id; // Spotify Track ID
  private final String name;
  private final String artistName;
  private final String albumName;
  private final String albumImageUrl;
  private final String previewUrl; // Null 값일 때도 간혹 존재. 이 부분 고려해야함.
  private final String duration;

  // 생성자
  public TrackDto(String id, String name, String artistName, String albumName,
      String albumImageUrl, String previewUrl, Integer durationMS) {
    this.id = id;
    this.name = name;
    this.artistName = artistName;
    this.albumName = albumName;
    this.albumImageUrl = albumImageUrl;
    this.previewUrl = previewUrl;
    this.duration = formatDuration(durationMS);
  }

  // Spotify API는 ms 단위로 재생시간이 들어온다.
  // 이것을 분:초로 변환 (예 : 03:30초)
  private String formatDuration(Integer durationMs) {
    if (durationMs == null) {
      return "00:00";
    }
    long minutes = (durationMs / 1000) / 60;
    long seconds = (durationMs / 1000) % 60;
    return String.format("%02d:%02d", minutes, seconds);
  }
}
