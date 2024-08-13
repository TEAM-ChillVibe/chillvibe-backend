package com.chillvibe.chillvibe.domain.playlist.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

/**
 * 빈 플레이리스트를 생성할 때 사용하는 DTO 입니다.
 * <p>
 * - title : 플레이리스트의 제목 <br>
 */
@Getter
@Setter
public class PlaylistCreateRequestDto {
  @NotBlank(message = "플레이리스트 제목을 입력하세요.")
  @Size(min = 1, max = 50, message = "플레이리스트의 제목은 1자 이상, 50자 이하여야 합니다.")
  private String title;
}
