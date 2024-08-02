package com.chillvibe.chillvibe.domain.spotify.service;

import com.chillvibe.chillvibe.domain.spotify.dto.SpotifySearchResult;
import com.chillvibe.chillvibe.domain.spotify.dto.TrackSearchDto;
import com.chillvibe.chillvibe.global.error.ErrorCode;
import com.chillvibe.chillvibe.global.error.exception.ApiException;
import com.neovisionaries.i18n.CountryCode;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import se.michaelthelin.spotify.SpotifyApi;
import se.michaelthelin.spotify.model_objects.specification.Paging;
import se.michaelthelin.spotify.model_objects.specification.Track;
import se.michaelthelin.spotify.requests.data.search.simplified.SearchTracksRequest;

@Service
@Slf4j
public class SpotifyService {

  private final SpotifyApi spotifyApi;

  @Autowired
  public SpotifyService(SpotifyApi spotifyApi) {
    this.spotifyApi = spotifyApi;
  }

  public SpotifySearchResult searchTracks(String query, int offset, int limit) {
    try {
      SearchTracksRequest searchRequest = spotifyApi.searchTracks(query)
          .market(CountryCode.KR)
          .limit(limit)
          .offset(offset)
          .build();

      Paging<Track> trackPaging = searchRequest.execute();

      List<TrackSearchDto> tracks = Arrays.stream(trackPaging.getItems())
          .map(this::convertToTrackSearchDto)
          .collect(Collectors.toList());

      return new SpotifySearchResult(
          tracks,
          trackPaging.getTotal(),
          offset + tracks.size() < trackPaging.getTotal()
      );
    } catch (Exception e) {
      throw new ApiException(ErrorCode.SPOTIFY_API_ERROR);
    }
  }

  // API로 가져온 정보 Dto 변환
  private TrackSearchDto convertToTrackSearchDto(Track track) {
    return new TrackSearchDto(
        track.getId(),
        track.getName(),
        track.getArtists()[0].getName(),
        track.getAlbum().getImages()[0].getUrl(),
        track.getPreviewUrl(),
        track.getDurationMs()
    );
  }
}