package com.chillvibe.chillvibe.domain.spotify.controller;

import com.chillvibe.chillvibe.domain.spotify.dto.TrackSearchDto;
import com.chillvibe.chillvibe.domain.spotify.service.SpotifyService;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/tracks")
@Tag(name = "Track", description = "트랙 API (Spotify API)")
public class TrackController {

  private final SpotifyService spotifyService;

  public TrackController(SpotifyService spotifyService){
    this.spotifyService = spotifyService;
  }

  @GetMapping("/search")
  public ResponseEntity<Page<TrackSearchDto>> getTracksSearchResults(
      @RequestParam String query,
      @RequestParam(defaultValue = "0") int page,
      @RequestParam(defaultValue = "20") int size) {
    Pageable pageable = PageRequest.of(page, size);
    Page<TrackSearchDto> resultPage = spotifyService.searchTracks(query, pageable);
    return ResponseEntity.ok(resultPage);
  }

  @GetMapping("/recommendations")
  public ResponseEntity<List<TrackSearchDto>> getRecommendations(
      @RequestParam(defaultValue = "relax") String category
  ){
    List<TrackSearchDto> tracks = spotifyService.recommendTracks(category);
    return ResponseEntity.ok(tracks);
  }

}
