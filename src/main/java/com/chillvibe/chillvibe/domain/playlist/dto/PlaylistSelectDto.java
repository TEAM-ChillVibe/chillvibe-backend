package com.chillvibe.chillvibe.domain.playlist.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

// 모달창에는 플레이리스트의 ID와 제목들만 전달되면 된다.
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PlaylistSelectDto {
  private Long id;
  private String title;
}
