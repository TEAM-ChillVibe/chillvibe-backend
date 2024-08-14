package com.chillvibe.chillvibe.domain.spotify.controller;

import com.chillvibe.chillvibe.domain.spotify.dto.FeaturedPlaylistResponseDto;
import com.chillvibe.chillvibe.domain.spotify.dto.TrackResponseDto;
import com.chillvibe.chillvibe.domain.spotify.service.SpotifyService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.concurrent.CompletableFuture;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
@RequestMapping("/api/tracks")
@Tag(name = "Track", description = "트랙 API (Spotify API)")
public class TrackController {

  private final SpotifyService spotifyService;

  public TrackController(SpotifyService spotifyService){
    this.spotifyService = spotifyService;
  }

  @Operation(summary = "트랙 검색", description = "Spotify API를 이용하여 트랙을 검색하는데 사용하는 API")
  @GetMapping("/search")
  public ResponseEntity<Page<TrackResponseDto>> getTracksSearchResults(
      @RequestParam String query,
      @RequestParam(defaultValue = "0") int page,
      @RequestParam(defaultValue = "20") int size) {
    Pageable pageable = PageRequest.of(page, size);
    Page<TrackResponseDto> resultPage = spotifyService.searchTracks(query, pageable);
    return ResponseEntity.ok(resultPage);
  }

  @Operation(summary = "Spotify 인기 플레이리스트 가져오기", description = "Spotify API를 이용하여 인기 플레이리스트를 가져옵니다.")
  @GetMapping("/featured-playlists")
  public CompletableFuture<ResponseEntity<FeaturedPlaylistResponseDto>> getFeaturedPlaylist(
      @RequestParam(defaultValue = "ko_KR") String locale,
      @RequestParam(defaultValue = "0") int page,
      @RequestParam(defaultValue = "5") int size) {
    return spotifyService.getFeaturedPlaylist(locale, page, size)
        .thenApply(ResponseEntity::ok)
        .exceptionally(ex -> {
          log.error("Error fetching featured playlist", ex);
          return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        });
  }
}
