package com.chillvibe.chillvibe.domain.playlist.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * PlaylistListItemMini 컴포넌트에 필요한 값들 입니다. <br>
 * 마이페이지의 플레이리스트, 새 게시글 생성, 게시글 수정에 사용됩니다.
 * <p>
 * - id : 플레이리스트 ID <br>
 * - title : 플레이리스트 제목 <br>
 * - trackCount : 플레이리스트에 담긴 총 트랙 수 <br>
 * - thumbnailUrl : 플레이리스트 썸네일 <br>
 */
@Getter
@Setter
@NoArgsConstructor
public class PlaylistSimpleResponseDto {
  private Long id;
  private String title;
  private int trackCount;
  private String thumbnailUrl;
}
