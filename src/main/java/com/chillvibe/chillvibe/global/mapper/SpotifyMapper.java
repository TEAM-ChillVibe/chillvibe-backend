package com.chillvibe.chillvibe.global.mapper;

import com.chillvibe.chillvibe.domain.spotify.dto.TrackResponseDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import se.michaelthelin.spotify.model_objects.specification.Track;

@Mapper(componentModel = "spring")
public interface SpotifyMapper {

  // API로 가져온 정보를 다 가져오지 말고 필요한 정보만 Dto로 변환
  @Mapping(target = "id", source = "id")
  @Mapping(target = "name", source = "name")
  @Mapping(target = "artist", expression = "java(track.getArtists()[0].getName())")
  @Mapping(target = "thumbnailUrl", expression = "java(track.getAlbum().getImages()[0].getUrl())")
  @Mapping(target = "previewUrl", source = "previewUrl")
  @Mapping(target = "duration", expression = "java(formatDuration(track.getDurationMs()))")
  TrackResponseDto trackToTrackSearchDto(Track track);

  // Spotify API는 ms 단위로 재생시간이 들어온다.
  // 이것을 분:초로 변환 (예 : 03:30초)
  default String formatDuration(Integer durationMs) {
    if (durationMs == null) {
      return "00:00";
    }
    long minutes = (durationMs / 1000) / 60;
    long seconds = (durationMs / 1000) % 60;
    return String.format("%02d:%02d", minutes, seconds);
  }
}