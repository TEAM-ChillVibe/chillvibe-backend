package com.chillvibe.chillvibe.domain.playlist.service;

import com.chillvibe.chillvibe.domain.playlist.dto.PlaylistCreateRequestDto;
import com.chillvibe.chillvibe.domain.playlist.dto.PlaylistResponseDto;
import com.chillvibe.chillvibe.domain.playlist.dto.PlaylistSelectResponseDto;
import com.chillvibe.chillvibe.domain.playlist.dto.PlaylistSimpleResponseDto;
import com.chillvibe.chillvibe.domain.playlist.dto.PlaylistTrackRequestDto;
import com.chillvibe.chillvibe.domain.playlist.dto.PlaylistTrackResponseDto;
import java.util.List;
import org.springframework.data.domain.Page;

public interface PlaylistService {
  // ===== 토큰 필요 =====
  // 트랙 추가시 플레이리스트 선택 모달창 - 나의 플레이리스트들 출력
  List<PlaylistSelectResponseDto> getUserPlaylistsForSelection();
  // 마이 페이지, 게시글 생성 페이지 - 나의 플레이리스트들 조회
  Page<PlaylistSimpleResponseDto> getMyPlaylists(int page, int size);
  // 플레이리스트 상세(수정) 페이지 조회
  PlaylistResponseDto getPlaylistForEditing(Long playlistId);
  // 마이 페이지 - 빈 플레이리스트 생성
  Long createEmptyPlaylist(String title);
  // 플레이리스트에 트랙 추가하기.
  PlaylistTrackResponseDto addTrackToPlaylist(Long PlaylistId, PlaylistTrackRequestDto requestDto);
  // 플레이리스트 제목 수정
  void editPlaylistTitle(PlaylistCreateRequestDto playlistCreateRequestDto, Long playlistId);
  // 플레이리스트 상세(수정) 페이지 - 플레이리스트 삭제
  void deletePlaylist(Long playlistId);
  // 플레이리스트 상세(수정) 페이지 - 선택한 트랙들 삭제하기
  void removeTracksFromPlaylist(Long playlistId, List<Long> trackIds);
  // 게시글 수정 페이지 - PostId로 해당 게시글에 있는 플레이리스트 찾기
  PlaylistSimpleResponseDto getPlaylistByPostId(Long PostId);
  // 플레이리스트 상세(수정) 페이지 - 플레이리스트의 트랙들 조회
  List<PlaylistTrackResponseDto> getMyPlaylistTracks(Long playlistId);

  // ===== 토큰 불필요 =====

}
