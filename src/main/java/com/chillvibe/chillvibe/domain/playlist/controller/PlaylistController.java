package com.chillvibe.chillvibe.domain.playlist.controller;

import com.chillvibe.chillvibe.domain.playlist.dto.PlaylistCreateRequestDto;
import com.chillvibe.chillvibe.domain.playlist.dto.PlaylistResponseDto;
import com.chillvibe.chillvibe.domain.playlist.dto.PlaylistSelectResponseDto;
import com.chillvibe.chillvibe.domain.playlist.dto.PlaylistSimpleResponseDto;
import com.chillvibe.chillvibe.domain.playlist.dto.PlaylistTrackRequestDto;
import com.chillvibe.chillvibe.domain.playlist.dto.PlaylistTrackResponseDto;
import com.chillvibe.chillvibe.domain.playlist.service.PlaylistService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
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
@Tag(name = "Playlist", description = "플레이리스트 API")
public class PlaylistController {

  private final PlaylistService playlistService;

  @Operation(summary = "빈 플레이리스트 생성", description = "유저가 제목을 입력하여 빈 플레이리스트를 생성하는데 사용하는 API")
  @PostMapping
  public ResponseEntity<Long> createPlaylist(@RequestBody @Valid PlaylistCreateRequestDto request) {
    Long playlistId = playlistService.createEmptyPlaylist(request.getTitle());
    return ResponseEntity.ok(playlistId);
  }

  @Operation(summary = "나의 플레이리스트 조회 (제목)", description = "트랙 추가를 위해 본인의 플레이리스트를 확인하는데 사용하는 API")
  @GetMapping("/selection")
  public ResponseEntity<List<PlaylistSelectResponseDto>> getPlaylistsForSelection() {
    List<PlaylistSelectResponseDto> playlists = playlistService.getUserPlaylistsForSelection();
    return ResponseEntity.ok(playlists);
  }

  @Operation(summary = "플레이리스트 수정(상세) 페이지 조회", description = "트랙 추가를 위해 본인의 플레이리스트를 확인하는데 사용하는 API")
  @GetMapping("/edit")
  public ResponseEntity<PlaylistResponseDto> getPlaylistForEditing(@RequestParam Long playlistId){
    PlaylistResponseDto responseDto = playlistService.getPlaylistForEditing(playlistId);
    return ResponseEntity.ok(responseDto);
  }

  @Operation(summary = "나의 플레이리스트 조회 (제목, 총 트랙 수, 썸네일 이미지) ", description = "마이페이지, 게시글 생성 페이지에서 본인의 플레이리스트를 확인하는데 사용하는 API")
  // 최신 게시글이 맨 앞에 나와야 한다.
  @GetMapping("/my")
  public ResponseEntity<Page<PlaylistSimpleResponseDto>> getMyPlaylists(
      @RequestParam(defaultValue = "0") int page,
      @RequestParam(defaultValue = "10") int size) {
    Page<PlaylistSimpleResponseDto> playlists = playlistService.getMyPlaylists(page,size);
    return ResponseEntity.ok(playlists);
  }

  @Operation(summary = "플레이리스트 삭제", description = "본인의 플레이리스트를 삭제하는데 사용하는 API")
  @DeleteMapping
  public ResponseEntity<Void> deletePlaylist(@RequestParam Long playlistId){
    playlistService.deletePlaylist(playlistId);
    return ResponseEntity.noContent().build();
  }

  @Operation(summary = "플레이리스트 트랙 추가", description = "플레이리스트를 선택하고, 해당 플레이리스트에 트랙을 추가하는데 사용하는 API")
  @PostMapping("/track")
  public ResponseEntity<Long> addTrackToPlaylist(@RequestParam Long playlistId, @RequestBody PlaylistTrackRequestDto requestDto) {
    PlaylistTrackResponseDto addedTrack = playlistService.addTrackToPlaylist(playlistId, requestDto);
    return ResponseEntity.ok(addedTrack.getId());
  }

  @Operation(summary = "플레이리스트 트랙 삭제", description = "플레이리스트를 선택하고, 해당 플레이리스트에 트랙을 추가하는데 사용하는 API")
  @DeleteMapping("/{playlistId}/tracks")
  public ResponseEntity<Void> removeTracksFromPlaylist(@PathVariable Long playlistId, @RequestBody List<Long> trackIds) {
    playlistService.removeTracksFromPlaylist(playlistId, trackIds);
    return ResponseEntity.noContent().build();
  }

  @Operation(summary = "게시글의 플레이리스트 가져오기", description = "게시글 수정 페이지에 나오는 플레이리스트를 띄우는데 사용하는 API")
  @GetMapping("/post/{postId}")
  public ResponseEntity<PlaylistSimpleResponseDto> getPlaylistByPostId(@PathVariable Long postId) {
    PlaylistSimpleResponseDto playlist = playlistService.getPlaylistByPostId(postId);
    return ResponseEntity.ok(playlist);
  }

  // 플레이리스트 수정 페이지 - 트랙들 가져오기.
  @Operation(summary = "나의 플레이리스트 트랙들 가져오기", description = "플레이리스트 상세(수정) 페이지에 나오는 트랙들을 가져오는데 사용하는 API")
  @GetMapping("/my/{playlistId}/tracks")
  public ResponseEntity<List<PlaylistTrackResponseDto>> getMyPlaylistTracks(@PathVariable Long playlistId){
    List<PlaylistTrackResponseDto> tracks = playlistService.getMyPlaylistTracks(playlistId);
    return ResponseEntity.ok(tracks);
  }
}
