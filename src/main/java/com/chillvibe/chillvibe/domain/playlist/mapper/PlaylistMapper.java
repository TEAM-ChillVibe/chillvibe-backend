package com.chillvibe.chillvibe.domain.playlist.mapper;

import com.chillvibe.chillvibe.domain.playlist.dto.PlaylistResponseDto;
import com.chillvibe.chillvibe.domain.playlist.entity.Playlist;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.data.domain.Page;

@Mapper(componentModel = "spring")
public interface PlaylistMapper {
  @Mapping(target = "trackCount", expression = "java(playlist.getTracks().size())")
  PlaylistResponseDto playlistToPlaylistDto(Playlist playlist);

  default Page<PlaylistResponseDto> playlistPageToPlaylistDtoPage(Page<Playlist> playlistPage) {
    return playlistPage.map(this::playlistToPlaylistDto);
  }
}
