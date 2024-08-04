package com.chillvibe.chillvibe.domain.playlist.service;

import com.chillvibe.chillvibe.domain.playlist.dto.PlaylistEditPageResponseDto;
import com.chillvibe.chillvibe.domain.playlist.dto.PlaylistSelectDto;
import com.chillvibe.chillvibe.domain.playlist.dto.PlaylistTrackRequestDto;
import com.chillvibe.chillvibe.domain.playlist.dto.PlaylistTrackResponseDto;
import com.chillvibe.chillvibe.domain.playlist.entity.Playlist;
import com.chillvibe.chillvibe.domain.playlist.entity.PlaylistTrack;
import com.chillvibe.chillvibe.domain.playlist.mapper.PlaylistMapper;
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
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class PlaylistServiceImpl implements PlaylistService {

  private final PlaylistRepository playlistRepository;
  private final PlaylistTrackRepository playlistTrackRepository;
  private final PlaylistMapper playlistMapper;
  private final PlaylistTrackMapper playlistTrackMapper;
  private final UserRepository userRepository;
  private final UserUtil userUtil;

  // 플레이리스트 기본 이미지 입니다.
  @Value("${playlist.default.image.url}")
  private String defaultImageUrl;

  public PlaylistServiceImpl(PlaylistRepository playlistRepository,
      PlaylistTrackRepository playlistTrackRepository,
      PlaylistMapper playlistMapper,
      PlaylistTrackMapper playlistTrackMapper,
      UserRepository userRepository,
      UserUtil userUtil){
    this.playlistRepository = playlistRepository;
    this.playlistTrackRepository = playlistTrackRepository;
    this.playlistMapper = playlistMapper;
    this.playlistTrackMapper = playlistTrackMapper;
    this.userRepository = userRepository;
    this.userUtil = userUtil;
  }

  // 플레이리스트에 트랙을 추가하려는 유저에게 본인이 가진 플레이리스트들을 보여준다.
  @Override
  public List<PlaylistSelectDto> getUserPlaylistsForSelection() {
    // 현재 해당 작업을 시작하려는 유저를 확인한다.
    Long currentUserId = userUtil.getAuthenticatedUserId();
    if (currentUserId == null) {
      throw new ApiException(ErrorCode.UNAUTHENTICATED);
    }

    List<Playlist> playlists = playlistRepository.findByUserId(currentUserId);

    return playlistMapper.playlistListToPlaylistSelectDtoList(playlists);
  }

  // 로그인 한 유저가 빈 플레이리스트를 생성한다.
  @Override
  @Transactional
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

  @Override
  @Transactional
  public void deletePlaylist(Long playlistId){
    // 해당 작업을 진행하려는 유저를 확인한다.
    Long currentUserId = userUtil.getAuthenticatedUserId();
    if(currentUserId == null){
      throw new ApiException(ErrorCode.UNAUTHENTICATED);
    }

    Playlist playlist = playlistRepository.findById(playlistId)
        .orElseThrow(() -> new ApiException(ErrorCode.PLAYLIST_NOT_FOUND));

    // 삭제를 사도하려는 유저와 플레이리스트의 주인이 맞는지 확인한다.
    if (!playlist.getUser().getId().equals(currentUserId)) {
      throw new ApiException(ErrorCode.UNAUTHORIZED_ACCESS);
    }
    playlistRepository.delete(playlist);
  }

  @Override
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

  @Override
  public PlaylistEditPageResponseDto getPlaylistForEditing(Long playlistId){
    Long currentUserId = userUtil.getAuthenticatedUserId();
    if (currentUserId == null ){
      throw new ApiException(ErrorCode.UNAUTHENTICATED);
    }

    Playlist playlist =  playlistRepository.findById(playlistId).orElseThrow(() -> new ApiException(ErrorCode.PLAYLIST_NOT_FOUND));

    if (!playlist.getUser().getId().equals(currentUserId)) {
      throw new ApiException(ErrorCode.UNAUTHORIZED_ACCESS);
    }

    List<PlaylistTrack> tracks = playlistTrackRepository.findByPlaylistId(playlistId);
    List<PlaylistTrackResponseDto> trackDtos = playlistTrackMapper.toDtoList(tracks);

    return PlaylistEditPageResponseDto.builder()
        .playlistName(playlist.getTitle())
        .trackCount(tracks.size())
        .createdAt(playlist.getCreatedAt())
        .modifiedAt(playlist.getModifiedAt())
        .tracks(trackDtos)
        .build();

  }

  @Override
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

      PlaylistTrack savedTrack = playlistTrackRepository.save(playlistTrack);
      return playlistTrackMapper.toDto(savedTrack);
  }

  @Override
  @Transactional
  public void removeTracksFromPlaylist(Long playlistId, List<Long> trackIds) {
    Playlist playlist = playlistRepository.findById(playlistId).orElseThrow(() -> new ApiException(ErrorCode.PLAYLIST_NOT_FOUND));

    Long currentUserId = userUtil.getAuthenticatedUserId();
    if(currentUserId == null){
      throw new ApiException(ErrorCode.UNAUTHENTICATED);
    }

    if(!playlist.getUser().getId().equals(currentUserId)) {
      throw new ApiException(ErrorCode.UNAUTHORIZED_ACCESS);
    }

    List<PlaylistTrack> tracks = playlistTrackRepository.findAllById(trackIds);

    for (PlaylistTrack track : tracks) {
      if (!track.getPlaylist().equals(playlist)) {
        throw new ApiException(ErrorCode.TRACK_NOT_IN_PLAYLIST);
      }
      playlist.getTracks().remove(track);
      playlistTrackRepository.delete(track);
    }
  }

}
