package com.chillvibe.chillvibe.domain.playlist.dto;

import lombok.Getter;
import lombok.Setter;

/**
 * TrackListListItem, TrackListEditItem 컴포넌트에 필요한 값들 입니다. <br>
 * 담은 플레이리스트 트랙들을 보여줄 때 사용하는 DTO입니다.
 * <p>
 * - id : playlistTrack 엔티티의 ID입니다.<br>
 * - trackId : Spotify TrackId<br>
 * - name : 트랙 이름<br>
 * - artist : 아티스트 명<br>
 * - duration : 재생 시간<br>
 * - previewUrl : 미리듣기 Url<br>
 * - thumbnailUrl : 썸네일<br>
 */
@Getter
@Setter
public class PlaylistTrackResponseDto {
  private Long id;
  private String trackId;
  private String name;
  private String artist;
  private String duration;
  private String previewUrl;
  private String thumbnailUrl;
}
