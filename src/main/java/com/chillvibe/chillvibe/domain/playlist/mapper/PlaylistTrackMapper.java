package com.chillvibe.chillvibe.domain.playlist.mapper;

import com.chillvibe.chillvibe.domain.playlist.dto.PlaylistRequestDto;
import com.chillvibe.chillvibe.domain.playlist.dto.PlaylistTrackResponseDto;
import com.chillvibe.chillvibe.domain.playlist.entity.PlaylistTrack;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface PlaylistTrackMapper {

  PlaylistTrackResponseDto toDto(PlaylistTrack track);
//  @Mapping(target = "PlaylistTrack.id", ignore = true)
//  PlaylistTrack playlistTrackRequestDto(PlaylistRequestDto playlistRequestDto);

}
