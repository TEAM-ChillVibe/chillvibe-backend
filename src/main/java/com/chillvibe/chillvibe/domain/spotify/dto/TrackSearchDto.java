package com.chillvibe.chillvibe.domain.spotify.dto;

import lombok.Getter;

@Getter
public class TrackSearchDto {

  private final String id; // Spotify Track ID
  private final String name; // 노래 이름
  private final String artistName; // 아티스트 이름
  private final String albumImageUrl; // 커버 이미지
  private final String previewUrl; // 미리듣기 URL (Null 처리 프론트엔드에서 완료)
  private final String duration; // 재생 시간

  // 생성자
  public TrackSearchDto(String id, String name, String artistName,
      String albumImageUrl, String previewUrl, Integer durationMS) {
    this.id = id;
    this.name = name;
    this.artistName = artistName;
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
