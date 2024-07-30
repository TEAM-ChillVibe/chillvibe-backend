package com.chillvibe.chillvibe.domain.playlist.controller;

import com.chillvibe.chillvibe.domain.playlist.dto.PlaylistRequestDto;
import com.chillvibe.chillvibe.domain.playlist.dto.PlaylistTrackRequestDto;
import com.chillvibe.chillvibe.domain.playlist.dto.PlaylistTrackResponseDto;
import com.chillvibe.chillvibe.domain.playlist.entity.Playlist;
import com.chillvibe.chillvibe.domain.playlist.service.PlaylistService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/playlist")
public class PlaylistController {

  private final PlaylistService playlistService;

  public PlaylistController(PlaylistService playlistService){
    this.playlistService = playlistService;
  }

  // 빈 플레이리스트 생성
  @PostMapping
  public ResponseEntity<Playlist> createPlaylist(@RequestBody PlaylistRequestDto request) {
    Playlist createdPlaylist = playlistService.createEmptyPlaylist(request.getTitle());
    return ResponseEntity.ok(createdPlaylist);
  }

  // 플레이리스트에 트랙 추가
  @PostMapping("/{playlistId}/tracks")
  public ResponseEntity<PlaylistTrackResponseDto> addTrackToPlaylist(@PathVariable Long playlistId, @RequestBody PlaylistTrackRequestDto requestDto) {
    PlaylistTrackResponseDto addedTrack = playlistService.addTrackToPlaylist(playlistId, requestDto);
    return ResponseEntity.ok(addedTrack);
  }

  // 플레이리스트에 트랙 삭제
  @DeleteMapping("/{playlistId}/tracks/{trackId}")
  public ResponseEntity<Void> removeTrackFromPlaylist(@PathVariable Long playlistId, @PathVariable Long trackId) {
    playlistService.removeTrackFromPlaylist(playlistId, trackId);
    return ResponseEntity.noContent().build();
  }
}
