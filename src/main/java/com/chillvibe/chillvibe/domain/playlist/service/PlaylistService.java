package com.chillvibe.chillvibe.domain.playlist.service;

import com.chillvibe.chillvibe.domain.playlist.dto.PlaylistTrackRequestDto;
import com.chillvibe.chillvibe.domain.playlist.dto.PlaylistTrackResponseDto;
import com.chillvibe.chillvibe.domain.playlist.entity.Playlist;

public interface PlaylistService {
  // 빈 플레이리스트 생성
  Playlist createEmptyPlaylist(String title);
  // 플레이리스트에 트랙 추가하기.
  PlaylistTrackResponseDto addTrackToPlaylist(Long PlaylistId, PlaylistTrackRequestDto requestDto);
  // 플레이리스트 트랙 삭제하기
  void removeTrackFromPlaylist(Long playlistId, Long trackId);

}
