package com.chillvibe.chillvibe.domain.playlist.controller;

import com.chillvibe.chillvibe.domain.playlist.service.PlaylistTrackService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/playlist")
public class PlaylistTrackController {
  private final PlaylistTrackService playlistTrackService;

  public PlaylistTrackController (PlaylistTrackService playlistTrackService){
    this.playlistTrackService = playlistTrackService;
  }
}
