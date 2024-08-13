package com.chillvibe.chillvibe.domain.playlist.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Schema(description = "빈 플레이리스트 생성 DTO")
@Getter
@Setter
public class PlaylistCreateRequestDto {
  @NotBlank(message = "플레이리스트 제목을 입력하세요.")
  @Size(min = 1, max = 50, message = "플레이리스트의 제목은 1자 이상, 50자 이하여야 합니다.")
  @Schema(description = "플레이리스트 제목")
  private String title;
}
