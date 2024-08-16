package com.chillvibe.chillvibe.domain.playlist.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;

@Schema(description = "트랙을 추가할 플레이리스트를 선택할 때 사용하는 DTO")
@Getter
@Builder
public class PlaylistSelectResponseDto {

  @Schema(description = "트랙을 추가할 플레이리스트 ID")
  private Long id;

  @Schema(description = "트랙을 추가할 플레이리스트의 제목")
  private String title;
}
