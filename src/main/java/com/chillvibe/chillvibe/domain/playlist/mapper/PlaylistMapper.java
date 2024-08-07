package com.chillvibe.chillvibe.domain.playlist.mapper;

import com.chillvibe.chillvibe.domain.playlist.dto.PlaylistResponseDto;
import com.chillvibe.chillvibe.domain.playlist.dto.PlaylistSelectDto;
import com.chillvibe.chillvibe.domain.playlist.entity.Playlist;
import java.util.List;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.data.domain.Page;

@Mapper(componentModel = "spring")
public interface PlaylistMapper {

  @Mapping(target = "trackCount", expression = "java(playlist.getTracks().size())")
  PlaylistSelectDto playlistToPlaylistSelectDto(Playlist playlist);

  List<PlaylistSelectDto> playlistListToPlaylistSelectDtoList(List<Playlist> playlists);

  @Mapping(target = "trackCount", expression = "java(playlist.getTracks().size())")
  @Mapping(target = "thumbnailUrls", expression = "java(getThumbnailUrls(playlist))")
  PlaylistResponseDto playlistToPlaylistDto(Playlist playlist);

  default List<String> getThumbnailUrls(Playlist playlist) {
    return playlist.getTracks().stream()
        .map(track -> track.getThumbnailUrl())
        .limit(4)
        .toList();
  }

  default Page<PlaylistResponseDto> playlistPageToPlaylistDtoPage(Page<Playlist> playlistPage) {
    return playlistPage.map(this::playlistToPlaylistDto);
  }
}
