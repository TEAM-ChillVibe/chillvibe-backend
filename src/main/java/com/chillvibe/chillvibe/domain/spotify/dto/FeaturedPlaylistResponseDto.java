package com.chillvibe.chillvibe.domain.spotify.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;

@Schema(description = "Spotify의 인기 플레이리스트를 가져오는데 사용하는 DTO")
@Data
@AllArgsConstructor
public class FeaturedPlaylistResponseDto {
  @Schema(description = "메세지 - 인기 플레이리스트")
  private String message;
  @Schema(description = "플레이리스트 이름")
  private String name;
  @Schema(description = "플레이리스트 썸네일 URL")
  private String imageUrl;
  @Schema(description = "플레이리스트에 들어있는 트랙들")
  private List<TrackSearchDto> tracks;
  @Schema(description = "총 곡의 수")
  private long totalTracks; // 총 곡의 수
  @Schema(description = "총 페이지")
  private int totalPages;
  @Schema(description = "현제 페이지")
  private int currentPage; // 현재 페이지

}