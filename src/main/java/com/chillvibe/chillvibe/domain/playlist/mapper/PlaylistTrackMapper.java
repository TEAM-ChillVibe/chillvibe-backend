package com.chillvibe.chillvibe.domain.playlist.mapper;

import com.chillvibe.chillvibe.domain.playlist.dto.PlaylistTrackResponseDto;
import com.chillvibe.chillvibe.domain.playlist.entity.PlaylistTrack;

public class PlaylistTrackMapper {
  public static PlaylistTrackResponseDto toDto(PlaylistTrack track){
    PlaylistTrackResponseDto dto = new PlaylistTrackResponseDto();
    dto.setId(track.getId());
    dto.setTrackId(track.getTrackId());
    dto.setName(track.getName());
    dto.setArtist(track.getArtist());
    dto.setDuration(track.getDuration());
    dto.setPreviewUrl(track.getPreviewUrl());
    dto.setThumbnailUrl(track.getThumbnailUrl());
    return dto;
  }

}
