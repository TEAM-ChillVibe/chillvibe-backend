//package com.chillvibe.chillvibe.domain.spotify.service;
//
//import com.chillvibe.chillvibe.domain.spotify.dto.SpotifySearchResult;
//import com.chillvibe.chillvibe.domain.spotify.dto.TrackDto;
//import com.neovisionaries.i18n.CountryCode;
//import java.util.Arrays;
//import java.util.List;
//import java.util.stream.Collectors;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//import se.michaelthelin.spotify.SpotifyApi;
//import se.michaelthelin.spotify.model_objects.specification.Paging;
//import se.michaelthelin.spotify.model_objects.specification.Track;
//import se.michaelthelin.spotify.requests.data.search.simplified.SearchTracksRequest;
//
//@Service
//@Slf4j
//public class SpotifyService {
//
//  private final SpotifyApi spotifyApi;
//
//  @Autowired
//  public SpotifyService(SpotifyApi spotifyApi) {
//    this.spotifyApi = spotifyApi;
//  }
//
//  public SpotifySearchResult searchTracks(String query, Integer offset, int limit) {
//    try {
//      SearchTracksRequest searchRequest = spotifyApi.searchTracks(query)
//          .market(CountryCode.KR)
//          .limit(limit)
//          .offset(offset != null ? offset : 0)
//          .build();
//
//      Paging<Track> trackPaging = searchRequest.execute();
//
//      List<TrackDto> tracks = Arrays.stream(trackPaging.getItems())
//          .map(this::convertToDto)
//          .collect(Collectors.toList());
//
//      return new SpotifySearchResult(
//          tracks,
//          trackPaging.getOffset() + trackPaging.getLimit(),
//          trackPaging.getNext() != null
//      );
//    } catch (Exception e) {
//      throw new RuntimeException("Error searching tracks", e);
//    }
//  }
//
//  // API로 가져온 정보 Dto 변환
//  private TrackDto convertToDto(Track track) {
//    return new TrackDto(
//        track.getId(),
//        track.getName(),
//        track.getArtists()[0].getName(),
//        track.getAlbum().getName(),
//        track.getAlbum().getImages()[0].getUrl(),
//        track.getPreviewUrl(),
//        track.getDurationMs()
//    );
//  }
//}