package com.chillvibe.chillvibe.domain.playlist.service;

import com.chillvibe.chillvibe.domain.playlist.dto.PlaylistEditPageResponseDto;
import com.chillvibe.chillvibe.domain.playlist.dto.PlaylistTrackRequestDto;
import com.chillvibe.chillvibe.domain.playlist.dto.PlaylistTrackResponseDto;
import com.chillvibe.chillvibe.domain.playlist.entity.Playlist;
import org.springframework.data.domain.Page;

public interface PlaylistService {
  // 빈 플레이리스트 생성
  Playlist createEmptyPlaylist(String title);
  // 마이 페이지 - 플레이리스트들 조회 (10개 단위 페이지네이션)
  Page<Playlist> getUserPlaylists(int page, int size);
  // 플레이리스트 수정(상세) 페이지 조회
  PlaylistEditPageResponseDto getPlaylistForEditing(Long playlistId);
  // 플레이리스트에 트랙 추가하기.
  PlaylistTrackResponseDto addTrackToPlaylist(Long PlaylistId, PlaylistTrackRequestDto requestDto);
  // 플레이리스트 트랙 삭제하기
  void removeTrackFromPlaylist(Long playlistId, Long trackId);

}
