package com.chillvibe.chillvibe.domain.playlist.service;

import com.chillvibe.chillvibe.domain.playlist.repository.PlaylistRepository;
import com.chillvibe.chillvibe.domain.playlist.repository.PlaylistTrackRepository;
import org.springframework.stereotype.Service;

@Service
public class PlaylistTrackService {

  private final PlaylistTrackRepository playlistTrackRepository;
  private final PlaylistRepository playlistRepository;

  public PlaylistTrackService(PlaylistTrackRepository playlistTrackRepository, PlaylistRepository playlistRepository){
    this.playlistTrackRepository = playlistTrackRepository;
    this.playlistRepository = playlistRepository;
  }


}
