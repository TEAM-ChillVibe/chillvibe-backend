package com.chillvibe.chillvibe.domain.spotify.service;

import com.chillvibe.chillvibe.domain.spotify.dto.FeaturedPlaylistResponseDto;
import com.chillvibe.chillvibe.domain.spotify.dto.TrackSearchDto;
import com.chillvibe.chillvibe.global.error.ErrorCode;
import com.chillvibe.chillvibe.global.error.exception.ApiException;
import com.chillvibe.chillvibe.global.mapper.SpotifyMapper;
import com.neovisionaries.i18n.CountryCode;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;
import lombok.extern.slf4j.Slf4j;
import org.apache.hc.core5.http.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import se.michaelthelin.spotify.SpotifyApi;
import se.michaelthelin.spotify.exceptions.SpotifyWebApiException;
import se.michaelthelin.spotify.model_objects.special.FeaturedPlaylists;
import se.michaelthelin.spotify.model_objects.specification.Paging;
import se.michaelthelin.spotify.model_objects.specification.PlaylistSimplified;
import se.michaelthelin.spotify.model_objects.specification.PlaylistTrack;
import se.michaelthelin.spotify.model_objects.specification.Recommendations;
import se.michaelthelin.spotify.model_objects.specification.Track;
import se.michaelthelin.spotify.model_objects.specification.TrackSimplified;
import se.michaelthelin.spotify.requests.data.browse.GetRecommendationsRequest;
import se.michaelthelin.spotify.requests.data.search.simplified.SearchTracksRequest;

@Service
@Slf4j
public class SpotifyService {

  private final SpotifyApi spotifyApi;
  private final SpotifyMapper spotifyMapper;

  // 모든 카테고리에 대해 일관된 인기도 설정
  private static final int TARGET_POPULARITY = 70;
  private static final int MIN_POPULARITY = 50;

  @Autowired
  public SpotifyService(SpotifyApi spotifyApi, SpotifyMapper spotifyMapper) {
    this.spotifyApi = spotifyApi;
    this.spotifyMapper = spotifyMapper;
  }

  public Page<TrackSearchDto> searchTracks(String query, Pageable pageable) {
    try {
      log.info("== 트랙 검색 API 호출 확인 == 검색어: {}", query);

      int offset = (int) pageable.getOffset();
      int limit = pageable.getPageSize();

      SearchTracksRequest searchRequest = spotifyApi.searchTracks(query)
          .market(CountryCode.KR)
          .limit(limit)
          .offset(offset)
          .build();

      // 우리가 구성한 페이지 구성으로 SPOTIFY에 페이지 요청
      Paging<Track> trackPaging = searchRequest.execute();

      List<TrackSearchDto> tracks = Arrays.stream(trackPaging.getItems())
          .map(spotifyMapper::trackToTrackSearchDto)
          .collect(Collectors.toList());

      // 페이지 인터페이스 구현
      // tracks = 현재 페이지에 포함된 실제 데이터 목록
      // pageable = 페이지네이션 정보
      // trackPaging.getTotal() = 전체 검색 결과의 총 항목
      return new PageImpl<>(tracks, pageable, trackPaging.getTotal());
    } catch (Exception e) {
      throw new ApiException(ErrorCode.SPOTIFY_API_ERROR);
    }
  }


  public CompletableFuture<FeaturedPlaylistResponseDto> getFeaturedPlaylist(String locale, int page, int size) {
    return spotifyApi.getListOfFeaturedPlaylists()
        .limit(1)
        .offset(0)
        .country(CountryCode.KR)
        .locale(locale)
        .build()
        .executeAsync()
        .thenCompose(featuredPlaylists -> {
          PlaylistSimplified playlist = featuredPlaylists.getPlaylists().getItems()[0];
          return getPlaylistTracks(playlist.getId(), page, size)
              .thenApply(tracksPage -> new FeaturedPlaylistResponseDto(
                  featuredPlaylists.getMessage(),
                  playlist.getName(),
                  playlist.getImages()[0].getUrl(),
                  tracksPage.getContent(),
                  tracksPage.getTotalElements(),
                  tracksPage.getTotalPages(),
                  tracksPage.getNumber()
              ));
        });
  }

  private CompletableFuture<Page<TrackSearchDto>> getPlaylistTracks(String playlistId, int page, int size) {
    return spotifyApi.getPlaylistsItems(playlistId)
        .limit(size)
        .offset(page * size)
        .build()
        .executeAsync()
        .thenApply(playlistTrackPaging -> {
          List<TrackSearchDto> tracks = Arrays.stream(playlistTrackPaging.getItems())
              .map(PlaylistTrack::getTrack)
              .filter(track -> track instanceof Track)
              .map(track -> (Track) track)
              .map(spotifyMapper::trackToTrackSearchDto)
              .collect(Collectors.toList());

          return new PageImpl<>(
              tracks,
              PageRequest.of(page, size),
              playlistTrackPaging.getTotal()
          );
        });
  }

}