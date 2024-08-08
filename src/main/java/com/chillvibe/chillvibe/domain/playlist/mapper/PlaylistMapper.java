package com.chillvibe.chillvibe.domain.playlist.mapper;

import com.chillvibe.chillvibe.domain.playlist.dto.PlaylistResponseDto;
import com.chillvibe.chillvibe.domain.playlist.dto.PlaylistSelectDto;
import com.chillvibe.chillvibe.domain.playlist.dto.PlaylistSimpleResponseDto;
import com.chillvibe.chillvibe.domain.playlist.entity.Playlist;
import java.util.List;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.data.domain.Page;

@Mapper(componentModel = "spring")
public interface PlaylistMapper {
  // Playlist -> PlaylistResponseDto
  PlaylistResponseDto playlistToPlaylistResponseDto(Playlist playlist);

  // Playlist -> PlaylistSelectDto
  PlaylistSelectDto playlistToPlaylistSelectDto(Playlist playlist);

  // List<Playlist> -> List<PlaylistSelectDto>
  List<PlaylistSelectDto> playlistListToPlaylistSelectDtoList(List<Playlist> playlists);
  // Playlist -> PlaylistSimpleResponseDto
  @Mapping(target = "trackCount", expression = "java(playlist.getTracks().size())")
  PlaylistSimpleResponseDto playlistToPlaylistSimpleResponseDto(Playlist playlist);
  // Page<Playlist> -> Page<PlaylistSimpleResponseDto>
  default Page<PlaylistSimpleResponseDto> playlistPageToPlaylistSimpleResponseDtoPage(Page<Playlist> playlistPage) {
    return playlistPage.map(this::playlistToPlaylistSimpleResponseDto);
  }
}
