package com.chillvibe.chillvibe.domain.playlist.controller;

import com.chillvibe.chillvibe.domain.playlist.dto.PlaylistEditPageResponseDto;
import com.chillvibe.chillvibe.domain.playlist.dto.PlaylistRequestDto;
import com.chillvibe.chillvibe.domain.playlist.dto.PlaylistResponseDto;
import com.chillvibe.chillvibe.domain.playlist.dto.PlaylistSelectDto;
import com.chillvibe.chillvibe.domain.playlist.dto.PlaylistTrackRequestDto;
import com.chillvibe.chillvibe.domain.playlist.dto.PlaylistTrackResponseDto;
import com.chillvibe.chillvibe.domain.playlist.entity.Playlist;
import com.chillvibe.chillvibe.domain.playlist.mapper.PlaylistMapper;
import com.chillvibe.chillvibe.domain.playlist.service.PlaylistService;
import java.util.List;
import org.apache.coyote.Response;
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
@RequestMapping("/api/playlist")
public class PlaylistController {

  private final PlaylistService playlistService;
  private final PlaylistMapper playlistMapper;

  public PlaylistController(PlaylistService playlistService, PlaylistMapper playlistMapper){
    this.playlistService = playlistService;
    this.playlistMapper = playlistMapper;
  }

  // 빈 플레이리스트 생성
  @PostMapping
  public ResponseEntity<Long> createPlaylist(@RequestBody PlaylistRequestDto request) {
    Playlist createdPlaylist = playlistService.createEmptyPlaylist(request.getTitle());
    return ResponseEntity.ok(createdPlaylist.getId());
  }

  // 본인의 플레이리스트 조회
  @GetMapping("/selection")
  public ResponseEntity<List<PlaylistSelectDto>> getPlaylistsForSelection() {
    List<PlaylistSelectDto> playlists = playlistService.getUserPlaylistsForSelection();
    return ResponseEntity.ok(playlists);
  }

  // 플레이리스트 수정(상세) 페이지 조회
  @GetMapping("/{playlistId}/edit")
  public ResponseEntity<PlaylistEditPageResponseDto> getPlaylistForEditing(@PathVariable Long playlistId){
    PlaylistEditPageResponseDto responseDto = playlistService.getPlaylistForEditing(playlistId);
    return ResponseEntity.ok(responseDto);
  }


  // 마이 페이지 - 플레이리스트들 조회 (10개 단위 페이지네이션)
  @GetMapping("/my")
  public ResponseEntity<Page<PlaylistResponseDto>> getMyPlaylists(@RequestParam(defaultValue = "0") int page,@RequestParam(defaultValue = "10") int size) {
    Page<Playlist> playlists = playlistService.getUserPlaylists(page,size);
    return ResponseEntity.ok(playlistMapper.playlistPageToPlaylistDtoPage(playlists));
  }

  // 플레이리스트 제거
  @DeleteMapping("/{playlistId}")
  public ResponseEntity<Void> deletePlaylist(@PathVariable Long playlistId){
    playlistService.deletePlaylist(playlistId);
    return ResponseEntity.noContent().build();
  }

  // 플레이리스트에 트랙 추가
  @PostMapping("/{playlistId}/tracks")
  public ResponseEntity<Long> addTrackToPlaylist(@PathVariable Long playlistId, @RequestBody PlaylistTrackRequestDto requestDto) {
    PlaylistTrackResponseDto addedTrack = playlistService.addTrackToPlaylist(playlistId, requestDto);
    return ResponseEntity.ok(addedTrack.getId());
  }

  // 플레이리스트에 트랙 삭제
  @DeleteMapping("/{playlistId}/tracks/{trackId}")
  public ResponseEntity<Void> removeTrackFromPlaylist(@PathVariable Long playlistId, @PathVariable Long trackId) {
    playlistService.removeTrackFromPlaylist(playlistId, trackId);
    return ResponseEntity.noContent().build();
  }
}
