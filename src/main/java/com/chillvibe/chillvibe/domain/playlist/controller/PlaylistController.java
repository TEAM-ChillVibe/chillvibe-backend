package com.chillvibe.chillvibe.domain.playlist.controller;

import com.chillvibe.chillvibe.domain.playlist.service.PlaylistService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/playlist")
public class PlaylistController {

  private final PlaylistService playlistService;

  public PlaylistController(PlaylistService playlistService){
    this.playlistService = playlistService;
  }

  // 빈 플레이리스트 생성

}
