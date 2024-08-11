package com.chillvibe.chillvibe.domain.spotify.dto;

import lombok.Getter;
import lombok.Value;

@Value
public class TrackSearchDto {
  String id; // Spotify Track ID
  String name; // 노래 이름
  String artist; // 아티스트 이름
  String thumbnailUrl; // 커버 이미지
  String previewUrl; // 미리듣기 URL (Null 처리 프론트엔드에서 완료)
  String duration; // 재생 시간
}
