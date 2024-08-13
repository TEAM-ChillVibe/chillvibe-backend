package com.chillvibe.chillvibe.domain.playlist.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import java.time.LocalDateTime;
import java.util.List;
import lombok.Getter;
import lombok.Setter;

@Schema(description = "플레이리스트 수정(상세) 페이지에 사용하는 DTO")
@Getter
@Setter
public class PlaylistResponseDto {
  @Schema(description = "플레이리스트 ID")
  private Long id;
  @Schema(description = "플레이리스트의 제목")
  private String title;
  @Schema(description = "플레이리스트 썸네일 URL")
  private String thumbnailUrl;
  @Schema(description = "플레이리스트 생성일")
  private LocalDateTime createdAt;
  @Schema(description = "플레이리스트 수정일")
  private LocalDateTime modifiedAt;
  @Schema(description = "플레이리스트 총 트랙 수")
  private int trackCount;
  @Schema(description = "플레이리스트에 들어있는 트랙들")
  private List<PlaylistTrackResponseDto> tracks;
}