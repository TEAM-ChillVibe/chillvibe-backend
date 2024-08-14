package com.chillvibe.chillvibe.domain.spotify.service;

import com.chillvibe.chillvibe.domain.spotify.dto.FeaturedPlaylistResponseDto;
import com.chillvibe.chillvibe.domain.spotify.dto.TrackResponseDto;
import java.util.concurrent.CompletableFuture;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;


public interface SpotifyService {
  // Spotify API를 이용해 트랙을 검색하여,  트랙의 검색 결과를 반환한다.
  Page<TrackResponseDto> searchTracks(String query, Pageable pageable);

  // Spotify API 트랙을 사용하여 현재 인기 플레이리스트를 가져온다.
  CompletableFuture<FeaturedPlaylistResponseDto> getFeaturedPlaylist(String locale, int page, int size) ;

}