package com.chillvibe.chillvibe.domain.playlist.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Schema(description = "PlaylistListItemMini 컴포넌트에 필요한 DTO \n\n" +
    "마이 페이지의 플레이리스트, 새 게시글 생성, 게시글 수정에 사용됩니다.")
@Getter
@Setter
@NoArgsConstructor
public class PlaylistSimpleResponseDto {
  @Schema(description = "플레이리스트 ID")
  private Long id;
  @Schema(description = "플레이리스트 제목")
  private String title;
  @Schema(description = "플레이리스트의 총 트랙 수")
  private int trackCount;
  @Schema(description = "플레이리스트 썸네일 URL")
  private String thumbnailUrl;
}
