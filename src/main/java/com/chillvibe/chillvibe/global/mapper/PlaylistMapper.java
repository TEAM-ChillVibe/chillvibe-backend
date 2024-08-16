package com.chillvibe.chillvibe.global.mapper;

import com.chillvibe.chillvibe.domain.playlist.dto.PlaylistResponseDto;
import com.chillvibe.chillvibe.domain.playlist.dto.PlaylistSelectResponseDto;
import com.chillvibe.chillvibe.domain.playlist.dto.PlaylistSimpleResponseDto;
import com.chillvibe.chillvibe.domain.playlist.dto.PlaylistTrackResponseDto;
import com.chillvibe.chillvibe.domain.playlist.entity.Playlist;
import java.util.List;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.data.domain.Page;

@Mapper(componentModel = "spring")
public interface PlaylistMapper {

  // Playlist -> PlaylistResponseDto
  @Mapping(target = "trackCount", expression = "java(playlist.getTracks().size())")
  @Mapping(target = "tracks", source = "tracks")
  PlaylistResponseDto playlistToPlaylistResponseDto(Playlist playlist, List<PlaylistTrackResponseDto> tracks);

  // Playlist -> PlaylistSelectDto
  PlaylistSelectResponseDto playlistToPlaylistSelectDto(Playlist playlist);

  // List<Playlist> -> List<PlaylistSelectDto>
  List<PlaylistSelectResponseDto> playlistListToPlaylistSelectDtoList(List<Playlist> playlists);
  // Playlist -> PlaylistSimpleResponseDto
  @Mapping(target = "trackCount", expression = "java(playlist.getTracks().size())")
  PlaylistSimpleResponseDto playlistToPlaylistSimpleResponseDto(Playlist playlist);

  // Page<Playlist> -> Page<PlaylistSimpleResponseDto>
  default Page<PlaylistSimpleResponseDto> playlistPageToPlaylistSimpleResponseDtoPage(Page<Playlist> playlistPage) {
    return playlistPage.map(this::playlistToPlaylistSimpleResponseDto);
  }
}
