package com.chillvibe.chillvibe.domain.playlist.service;

import com.chillvibe.chillvibe.domain.playlist.repository.PlaylistRepository;
import com.chillvibe.chillvibe.domain.playlist.repository.PlaylistTrackRepository;
import com.chillvibe.chillvibe.domain.post.repository.PostRepository;
import com.chillvibe.chillvibe.domain.user.repository.UserRepository;
import org.springframework.stereotype.Service;

@Service
public class PlaylistService {

  private final PlaylistRepository playlistRepository;
  private final PostRepository postRepository;
  private final UserRepository userRepository;

  public PlaylistService(PlaylistRepository playlistRepsository, UserRepository userRepository, PostRepository postRepository){
    this.playlistRepository = playlistRepsository;
    this.userRepository = userRepository;
    this.postRepository = postRepository;
  }

  // 빈 플레이리스트 생성 관련

}
