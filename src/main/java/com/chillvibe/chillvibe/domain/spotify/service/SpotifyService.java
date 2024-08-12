package com.chillvibe.chillvibe.domain.spotify.service;

import com.chillvibe.chillvibe.domain.spotify.dto.TrackSearchDto;
import com.chillvibe.chillvibe.global.error.ErrorCode;
import com.chillvibe.chillvibe.global.error.exception.ApiException;
import com.chillvibe.chillvibe.global.mapper.SpotifyMapper;
import com.neovisionaries.i18n.CountryCode;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import lombok.extern.slf4j.Slf4j;
import org.apache.hc.core5.http.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import se.michaelthelin.spotify.SpotifyApi;
import se.michaelthelin.spotify.exceptions.SpotifyWebApiException;
import se.michaelthelin.spotify.model_objects.specification.Paging;
import se.michaelthelin.spotify.model_objects.specification.Recommendations;
import se.michaelthelin.spotify.model_objects.specification.Track;
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

  public List<TrackSearchDto> recommendTracks(String category) {
    try {
      log.info("== 카테고리 기반 추천 트랙 API 호출 확인 == 카테고리: {}", category);

      float targetDanceability, targetEnergy, targetValence, targetTempo, targetInstrumentalness;
      String seedGenres;
      int limit = 6; // 기본값으로 6개의 트랙을 추천

      switch (category.toLowerCase()) {
        case "relax": // 휴식
          targetDanceability = 0.3f;
          targetEnergy = 0.2f;
          targetValence = 0.4f;
          targetTempo = 80f;
          targetInstrumentalness = 0.7f;
          seedGenres = "ambient,chill,classical";
          break;
        case "focus": // 집중
          targetDanceability = 0.4f;
          targetEnergy = 0.6f;
          targetValence = 0.5f;
          targetTempo = 110f;
          targetInstrumentalness = 0.5f;
          seedGenres = "electronic,classical,minimal";
          break;
        case "energize": // 활력
          targetDanceability = 0.8f;
          targetEnergy = 0.9f;
          targetValence = 0.7f;
          targetTempo = 130f;
          targetInstrumentalness = 0.1f;
          seedGenres = "pop,rock,electronic";
          break;
        case "romantic": // 로맨틱
          targetDanceability = 0.5f;
          targetEnergy = 0.4f;
          targetValence = 0.6f;
          targetTempo = 100f;
          targetInstrumentalness = 0.3f;
          seedGenres = "r-n-b,jazz,soul";
          break;
        case "melancholy": // 우울
          targetDanceability = 0.3f;
          targetEnergy = 0.3f;
          targetValence = 0.2f;
          targetTempo = 90f;
          targetInstrumentalness = 0.4f;
          seedGenres = "indie,folk,acoustic";
          break;
        default:
          throw new ApiException(ErrorCode.SPOTIFY_INVALID_CATEGORY);
      }

      // 해당 카테고리에 맞는 정보로 추천 API 요청
      GetRecommendationsRequest recommendationsRequest = spotifyApi.getRecommendations()
          .limit(limit)
          .market(CountryCode.KR)
          .target_danceability(targetDanceability)
          .target_energy(targetEnergy)
          .target_valence(targetValence)
          .target_tempo(targetTempo)
          .target_instrumentalness(targetInstrumentalness)
          .target_popularity(TARGET_POPULARITY)
          .min_popularity(MIN_POPULARITY)
          .seed_genres(seedGenres)
          .build();

      Recommendations recommendations = recommendationsRequest.execute();

      return Arrays.stream(recommendations.getTracks())
          .map(spotifyMapper::trackToTrackSearchDto)
          .collect(Collectors.toList());

    } catch (ApiException e) {
      throw e;
    } catch (Exception e) {
      throw new ApiException(ErrorCode.SPOTIFY_API_ERROR);
    }

  }


}