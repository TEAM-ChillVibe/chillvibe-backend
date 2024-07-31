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
import com.chillvibe.chillvibe.global.error.ErrorCode;
import com.chillvibe.chillvibe.global.error.exception.ApiException;
import com.chillvibe.chillvibe.global.jwt.util.UserUtil;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class PlaylistServiceImpl implements PlaylistService {

  private final PlaylistRepository playlistRepository;
  private final PlaylistTrackRepository playlistTrackRepository;
  private final PlaylistTrackMapper playlistTrackMapper;
  private final UserRepository userRepository;
  private final UserUtil userUtil;

  // 플레이리스트 기본 이미지 입니다.
  @Value("${playlist.default.image.url}")
  private String defaultImageUrl;

  public PlaylistServiceImpl(PlaylistRepository playlistRepository,
      PlaylistTrackRepository playlistTrackRepository,
      PlaylistTrackMapper playlistTrackMapper,
      UserRepository userRepository,
      UserUtil userUtil){
    this.playlistRepository = playlistRepository;
    this.playlistTrackRepository = playlistTrackRepository;
    this.playlistTrackMapper = playlistTrackMapper;
    this.userRepository = userRepository;
    this.userUtil = userUtil;
  }

  public Playlist createEmptyPlaylist(String title){
    Long userId = userUtil.getAuthenticatedUserId();
    if (userId == null) {
      throw new ApiException(ErrorCode.UNAUTHENTICATED); // 인증된 유저가 아닙니다.
    }

    User user = userRepository.findById(userId).orElseThrow(() -> new ApiException(ErrorCode.USER_NOT_FOUND));

    // 빈 플레이리스트 객체 생성
    Playlist playlist = Playlist.builder()
        .title(title)
        .user(user)
        .imageUrl(defaultImageUrl) // 이미지 호스팅 사이트에 업로드하고 가져왔습니다.
        .build();

    return playlistRepository.save(playlist);
  }

  public Page<Playlist> getUserPlaylists(int page, int size){
    Long userId = userUtil.getAuthenticatedUserId();
    if(userId == null) {
      throw new ApiException(ErrorCode.UNAUTHENTICATED);
    }

    if (!userRepository.existsById(userId)) {
      throw new ApiException(ErrorCode.USER_NOT_FOUND);
    }

    Pageable pageable = PageRequest.of(page,size);
    return playlistRepository.findByUserId(userId, pageable);
  }

  public PlaylistTrackResponseDto addTrackToPlaylist(Long playlistId, PlaylistTrackRequestDto requestDto){
    Playlist playlist = playlistRepository.findById(playlistId).orElseThrow(() -> new ApiException(
        ErrorCode.PLAYLIST_NOT_FOUND));

    Long currentUserId = userUtil.getAuthenticatedUserId();
    if (currentUserId == null){
      throw new ApiException(ErrorCode.UNAUTHENTICATED);
    }

    if (!playlist.getUser().getId().equals(currentUserId)) {
      throw new ApiException(ErrorCode.UNAUTHORIZED_ACCESS);
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

    try {
      PlaylistTrack savedTrack = playlistTrackRepository.save(playlistTrack);
      return playlistTrackMapper.toDto(savedTrack);
    } catch (Exception e) {
      throw new ApiException(ErrorCode.TRACK_ADD_FAILED);
    }

  }

  public void removeTrackFromPlaylist(Long playlistId, Long trackId) {
    Playlist playlist = playlistRepository.findById(playlistId).orElseThrow(() -> new ApiException(ErrorCode.PLAYLIST_NOT_FOUND));

    Long currentUserId = userUtil.getAuthenticatedUserId();
    if(!playlist.getUser().getId().equals(currentUserId)) {
      throw new ApiException(ErrorCode.UNAUTHORIZED_ACCESS);
    }

    PlaylistTrack track = playlistTrackRepository.findByPlaylistIdAndId(playlistId, trackId).orElseThrow(() -> new ApiException(ErrorCode.TRACK_NOT_FOUND));

    playlistTrackRepository.delete(track);
  }



}
