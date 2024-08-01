package com.chillvibe.chillvibe.domain.spotify.dto;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class SpotifySearchResult {
  private List<TrackSearchDto> tracks;
  private long totalTracks;
  private boolean hasMore;
}