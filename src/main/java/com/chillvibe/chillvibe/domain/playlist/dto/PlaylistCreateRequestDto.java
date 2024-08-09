package com.chillvibe.chillvibe.domain.playlist.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

// 플레이리스트를 생성할 때 사용합니다.
@Getter
@Setter
public class PlaylistCreateRequestDto {
  @NotBlank(message = "플레이리스트 제목을 입력하세요.")
  private String title;
}
