package com.chillvibe.chillvibe.domain.playlist.dto;

import lombok.Builder;
import lombok.Getter;

/**
 * 트랙을 추가할 플레이리스트를 선택할 때 사용하는 DTO입니다.
 * <p>
 * - id    : 트랙을 추가할 플레이리스트의 id <br>
 * - title : 트랙을 추가할 플레이리스트의 제목 <br>
 */
@Getter
@Builder
public class PlaylistSelectResponseDto {
  private Long id;
  private String title;
}
