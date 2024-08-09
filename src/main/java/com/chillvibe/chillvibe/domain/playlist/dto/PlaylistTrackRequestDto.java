package com.chillvibe.chillvibe.domain.playlist.dto;

import lombok.Getter;
import lombok.Setter;

/**
 * 검색 후, 플레이리스트에 트랙을 추가할 때 사용하는 DTO 입니다.
 * <p>
 * - trackId : Spotify TrackId<br>
 * - name : 트랙 이름<br>
 * - artist : 아티스트 명<br>
 * - duration : 재생 시간<br>
 * - previewUrl : 미리듣기 Url<br>
 * - thumbnailUrl : 썸네일<br>
 */
@Getter
@Setter
public class PlaylistTrackRequestDto {
  private String trackId;
  private String name;
  private String artist;
  private String duration;
  private String previewUrl;
  private String thumbnailUrl;
}
