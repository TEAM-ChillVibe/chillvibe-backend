package com.chillvibe.chillvibe.domain.playlist.controller;

import com.chillvibe.chillvibe.domain.playlist.dto.PlaylistCreateRequestDto;
import com.chillvibe.chillvibe.domain.playlist.dto.PlaylistResponseDto;
import com.chillvibe.chillvibe.domain.playlist.dto.PlaylistSelectResponseDto;
import com.chillvibe.chillvibe.domain.playlist.dto.PlaylistSimpleResponseDto;
import com.chillvibe.chillvibe.domain.playlist.dto.PlaylistTrackRequestDto;
import com.chillvibe.chillvibe.domain.playlist.dto.PlaylistTrackResponseDto;
import com.chillvibe.chillvibe.domain.playlist.service.PlaylistService;
import jakarta.validation.Valid;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/playlists")
@RequiredArgsConstructor
public class PlaylistController {

  private final PlaylistService playlistService;

  // 빈 플레이리스트 생성
  @PostMapping
  public ResponseEntity<Long> createPlaylist(@RequestBody @Valid PlaylistCreateRequestDto request) {
    Long playlistId = playlistService.createEmptyPlaylist(request.getTitle());
    return ResponseEntity.ok(playlistId);
  }

  // 본인의 플레이리스트 조회
  @GetMapping("/selection")
  public ResponseEntity<List<PlaylistSelectResponseDto>> getPlaylistsForSelection() {
    List<PlaylistSelectResponseDto> playlists = playlistService.getUserPlaylistsForSelection();
    return ResponseEntity.ok(playlists);
  }

  // 플레이리스트 수정(상세) 페이지 조회
  @GetMapping("/edit")
  public ResponseEntity<PlaylistResponseDto> getPlaylistForEditing(@RequestParam Long playlistId){
    PlaylistResponseDto responseDto = playlistService.getPlaylistForEditing(playlistId);
    return ResponseEntity.ok(responseDto);
  }


  // 마이 페이지 - 플레이리스트들 조회 (10개 단위 페이지네이션)
  // 최신 게시글이 맨 앞에 나와야 한다.
  @GetMapping("/my")
  public ResponseEntity<Page<PlaylistSimpleResponseDto>> getMyPlaylists(
      @RequestParam(defaultValue = "0") int page,
      @RequestParam(defaultValue = "10") int size) {
    Page<PlaylistSimpleResponseDto> playlists = playlistService.getMyPlaylists(page,size);
    return ResponseEntity.ok(playlists);
  }

  // 플레이리스트 제거
  @DeleteMapping
  public ResponseEntity<Void> deletePlaylist(@RequestParam Long playlistId){
    playlistService.deletePlaylist(playlistId);
    return ResponseEntity.noContent().build();
  }

  // 플레이리스트에 트랙 추가
  @PostMapping("/track")
  public ResponseEntity<Long> addTrackToPlaylist(@RequestParam Long playlistId, @RequestBody PlaylistTrackRequestDto requestDto) {
    PlaylistTrackResponseDto addedTrack = playlistService.addTrackToPlaylist(playlistId, requestDto);
    return ResponseEntity.ok(addedTrack.getId());
  }

  // 플레이리스트에 선택한 트랙들을 삭제
  @DeleteMapping("/{playlistId}/tracks")
  public ResponseEntity<Void> removeTracksFromPlaylist(@PathVariable Long playlistId, @RequestBody List<Long> trackIds) {
    playlistService.removeTracksFromPlaylist(playlistId, trackIds);
    return ResponseEntity.noContent().build();
  }

  // postId를 통해 플레이리스트 가져오기.
  @GetMapping("/post/{postId}")
  public ResponseEntity<PlaylistSimpleResponseDto> getPlaylistByPostId(@PathVariable Long postId) {
    PlaylistSimpleResponseDto playlist = playlistService.getPlaylistByPostId(postId);
    return ResponseEntity.ok(playlist);
  }

  // 플레이리스트 수정 페이지 - 트랙들 가져오기.
  @GetMapping("/my/{playlistId}/tracks")
  public ResponseEntity<List<PlaylistTrackResponseDto>> getMyPlaylistTracks(@PathVariable Long playlistId){
    List<PlaylistTrackResponseDto> tracks = playlistService.getMyPlaylistTracks(playlistId);
    return ResponseEntity.ok(tracks);
  }
}
