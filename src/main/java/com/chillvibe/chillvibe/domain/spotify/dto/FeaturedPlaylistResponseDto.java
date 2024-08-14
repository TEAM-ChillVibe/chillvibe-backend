package com.chillvibe.chillvibe.domain.spotify.dto;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class FeaturedPlaylistResponseDto {
  private String message; // 메세지 - 인기 플레이리스트
  private String name; // 플레이리스트 이름
  private String imageUrl; // 플레이리스트 썸네일
  private List<TrackSearchDto> tracks; // 플레이리스트에 들어있는 트랙들
  private long totalTracks; // 총 곡의 수
  private int totalPages; // 총 페이지
  private int currentPage; // 현재 페이지

}