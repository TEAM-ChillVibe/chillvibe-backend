package com.chillvibe.chillvibe.domain.playlist.mapper;

import com.chillvibe.chillvibe.domain.playlist.dto.PlaylistTrackResponseDto;
import com.chillvibe.chillvibe.domain.playlist.entity.PlaylistTrack;
import org.mapstruct.Mapper;

import java.util.List;


@Mapper(componentModel = "spring")
public interface PlaylistTrackMapper {
  PlaylistTrackResponseDto toDto(PlaylistTrack track);
  List<PlaylistTrackResponseDto> toDtoList(List<PlaylistTrack> tracks);

}
