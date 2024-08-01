package com.chillvibe.chillvibe.domain.playlist.dto;

import com.chillvibe.chillvibe.domain.playlist.entity.PlaylistTrack;
import java.time.LocalDateTime;
import java.util.List;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

// 필요한 정보
// 플레이리스트 이름, 플레이리스트 트랙 수, 플레이리스트 수정일(생성일도 그냥 가져오자.), 플레이리스트 트랙 수
@Getter
@Builder
public class PlaylistEditPageResponseDto {
  private String playlistName;
  private int trackCount;
  private LocalDateTime createdAt;
  private LocalDateTime modifiedAt;
  private List<PlaylistTrackResponseDto> tracks;
}