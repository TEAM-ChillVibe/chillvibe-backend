package com.chillvibe.chillvibe.domain.spotify.service;

import com.chillvibe.chillvibe.domain.spotify.dto.TrackSearchDto;
import com.chillvibe.chillvibe.global.error.ErrorCode;
import com.chillvibe.chillvibe.global.error.exception.ApiException;
import com.chillvibe.chillvibe.global.mapper.SpotifyMapper;
import com.neovisionaries.i18n.CountryCode;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import se.michaelthelin.spotify.SpotifyApi;
import se.michaelthelin.spotify.model_objects.specification.Paging;
import se.michaelthelin.spotify.model_objects.specification.Track;
import se.michaelthelin.spotify.requests.data.search.simplified.SearchTracksRequest;

@Service
@Slf4j
public class SpotifyService {

  private final SpotifyApi spotifyApi;
  private final SpotifyMapper spotifyMapper;

  @Autowired
  public SpotifyService(SpotifyApi spotifyApi, SpotifyMapper spotifyMapper) {
    this.spotifyApi = spotifyApi;
    this.spotifyMapper = spotifyMapper;
  }

  public Page<TrackSearchDto> searchTracks(String query, Pageable pageable) {
    try {
      log.info("== 트랙 검색 API 호출 확인 ==");
      // 현재 코드 상으로, 페이지를 누를때나, 계속 호출이 되게 됨.
      // 현재 SPOTIFY - 백 - 프론트 형태로,
      // SPOTIFY - 프론트로 API를 놓는 것도 고려 필요

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
}