package com.chillvibe.chillvibe.domain.playlist.service;

import com.chillvibe.chillvibe.domain.playlist.dto.PlaylistTrackRequestDto;
import com.chillvibe.chillvibe.domain.playlist.dto.PlaylistTrackResponseDto;
import com.chillvibe.chillvibe.domain.playlist.entity.Playlist;
import com.chillvibe.chillvibe.domain.playlist.entity.PlaylistTrack;
import com.chillvibe.chillvibe.domain.playlist.mapper.PlaylistTrackMapper;
import com.chillvibe.chillvibe.domain.playlist.repository.PlaylistRepository;
import com.chillvibe.chillvibe.domain.playlist.repository.PlaylistTrackRepository;
import com.chillvibe.chillvibe.domain.post.repository.PostRepository;
import com.chillvibe.chillvibe.domain.user.entity.User;
import com.chillvibe.chillvibe.domain.user.exception.UserNotFoundException;
import com.chillvibe.chillvibe.domain.user.repository.UserRepository;
import com.chillvibe.chillvibe.global.jwt.util.UserUtil;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class PlaylistServiceImpl implements PlaylistService {

  private final PlaylistRepository playlistRepository;
  private final PlaylistTrackRepository playlistTrackRepository;
  private final UserRepository userRepository;
  private final UserUtil userUtil;

  // 플레이리스트 기본 이미지 입니다.
  @Value("${playlist.default.image.url}")
  private String defaultImageUrl;

  public PlaylistServiceImpl(PlaylistRepository playlistRepository,
      PlaylistTrackRepository playlistTrackRepository,
      UserRepository userRepository,
      UserUtil userUtil){
    this.playlistRepository = playlistRepository;
    this.playlistTrackRepository = playlistTrackRepository;
    this.userRepository = userRepository;
    this.userUtil = userUtil;
  }

  public Playlist createEmptyPlaylist(String title){
    Long userId = userUtil.getAuthenticatedUserId();
    if (userId == null) {
      throw new RuntimeException("User not authenticated"); // 인증된 유저가 아닙니다.
    }

    User user = userRepository.findById(userId).orElseThrow(() -> new UserNotFoundException());

    // 빈 플레이리스트 객체 생성
    Playlist playlist = Playlist.builder()
        .title(title)
        .user(user)
        .imageUrl(defaultImageUrl) // 이미지 호스팅 사이트에 업로드하고 가져왔습니다.
        .build();

    return playlistRepository.save(playlist);
  }

  public PlaylistTrackResponseDto addTrackToPlaylist(Long playlistId, PlaylistTrackRequestDto requestDto){
    Playlist playlist = playlistRepository.findById(playlistId).orElseThrow(() -> new RuntimeException("플레이리스트를 찾을 수 없습니다."));

    Long currentUserId = userUtil.getAuthenticatedUserId();
    if (!playlist.getUser().getId().equals(currentUserId)) {
      throw new RuntimeException("해당 플레이리스트에 대한 권한이 없습니다.");
    }

    PlaylistTrack playlistTrack = PlaylistTrack.builder()
        .playlist(playlist)
        .trackId(requestDto.getTrackId())
        .name(requestDto.getName())
        .artist(requestDto.getArtist())
        .duration(requestDto.getDuration())
        .previewUrl(requestDto.getPreviewUrl())
        .thumbnailUrl(requestDto.getThumbnailUrl())
        .build();

    PlaylistTrack savedTrack = playlistTrackRepository.save(playlistTrack);

    return PlaylistTrackMapper.toDto(savedTrack);
  }

  public void removeTrackFromPlaylist(Long playlistId, Long trackId) {
    Playlist playlist = playlistRepository.findById(playlistId).orElseThrow(() -> new RuntimeException("플레이리스트를 찾을 수 없습니다."));

    Long currentUserId = userUtil.getAuthenticatedUserId();
    if(!playlist.getUser().getId().equals(currentUserId)) {
      throw new RuntimeException("해당 플레이리스트에 대한 권한이 없습니다.");
    }

    PlaylistTrack track = playlistTrackRepository.findByPlaylistIdAndId(playlistId, trackId).orElseThrow(() -> new RuntimeException("해당 트랙을 찾을 수 없습니다."));

    playlistTrackRepository.delete(track);
  }



}
