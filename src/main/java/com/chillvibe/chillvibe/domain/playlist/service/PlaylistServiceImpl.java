package com.chillvibe.chillvibe.domain.playlist.service;

import com.chillvibe.chillvibe.domain.playlist.dto.PlaylistEditPageResponseDto;
import com.chillvibe.chillvibe.domain.playlist.dto.PlaylistSelectResponseDto;
import com.chillvibe.chillvibe.domain.playlist.dto.PlaylistSimpleResponseDto;
import com.chillvibe.chillvibe.domain.playlist.dto.PlaylistTrackRequestDto;
import com.chillvibe.chillvibe.domain.playlist.dto.PlaylistTrackResponseDto;
import com.chillvibe.chillvibe.domain.playlist.entity.Playlist;
import com.chillvibe.chillvibe.domain.playlist.entity.PlaylistTrack;
import com.chillvibe.chillvibe.domain.playlist.mapper.PlaylistMapper;
import com.chillvibe.chillvibe.domain.playlist.mapper.PlaylistTrackMapper;
import com.chillvibe.chillvibe.domain.playlist.repository.PlaylistRepository;
import com.chillvibe.chillvibe.domain.playlist.repository.PlaylistTrackRepository;
import com.chillvibe.chillvibe.domain.user.entity.User;
import com.chillvibe.chillvibe.domain.user.repository.UserRepository;
import com.chillvibe.chillvibe.global.common.ThumbnailGenerator;
import com.chillvibe.chillvibe.global.error.ErrorCode;
import com.chillvibe.chillvibe.global.error.exception.ApiException;
import com.chillvibe.chillvibe.global.jwt.util.UserUtil;
import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class PlaylistServiceImpl implements PlaylistService {

  private final PlaylistRepository playlistRepository;
  private final PlaylistTrackRepository playlistTrackRepository;
  private final PlaylistMapper playlistMapper;
  private final PlaylistTrackMapper playlistTrackMapper;
  private final UserRepository userRepository;
  private final ThumbnailGenerator thumbnailGenerator;
  private final UserUtil userUtil;

  private Long getAuthenticatedUserIdOrThrow() {
    Long currentUserId = userUtil.getAuthenticatedUserId();
    if (currentUserId == null) {
      throw new ApiException(ErrorCode.UNAUTHENTICATED);
    }
    return currentUserId;
  }

  @Override
  public List<PlaylistSelectResponseDto> getUserPlaylistsForSelection() {
    Long currentUserId = getAuthenticatedUserIdOrThrow();
    List<Playlist> playlists = playlistRepository.findByUserId(currentUserId);
    return playlistMapper.playlistListToPlaylistSelectDtoList(playlists);
  }

  @Override
  @Transactional
  public Playlist createEmptyPlaylist(String title){
    Long currentUserId = getAuthenticatedUserIdOrThrow();

    User user = userRepository.findById(currentUserId)
        .orElseThrow(() -> new ApiException(ErrorCode.USER_NOT_FOUND));

    // 빈 플레이리스트 객체 생성
    Playlist playlist = Playlist.builder()
        .title(title)
        .user(user)
        .build();

    playlist = playlistRepository.save(playlist);

    // 기본 이미지 설정
    try {
      String thumbnailUrl = thumbnailGenerator.generateAndUploadThumbnail(
          Collections.emptyList(), "playlists", playlist.getId());
      playlist.updateImageUrl(thumbnailUrl);
    } catch (IOException e) {
      // 플레이리스트 삭제 (롤백)
      playlistRepository.delete(playlist);
      // ApiException 발생
      throw new ApiException(ErrorCode.THUMBNAIL_GENERATION_FAILED);
    }

    return playlist;
  }

  @Override
  @Transactional
  public void deletePlaylist(Long playlistId) {
    Long currentUserId = getAuthenticatedUserIdOrThrow();

    Playlist playlist = playlistRepository.findById(playlistId)
        .orElseThrow(() -> new ApiException(ErrorCode.PLAYLIST_NOT_FOUND));

    if (!playlist.getUser().getId().equals(currentUserId)) {
      throw new ApiException(ErrorCode.UNAUTHORIZED_ACCESS);
    }
    playlistRepository.delete(playlist);
  }

  @Override
  public Page<PlaylistSimpleResponseDto> getMyPlaylists(int page, int size) {
    Long currentUserId = getAuthenticatedUserIdOrThrow();

    if (!userRepository.existsById(currentUserId)) {
      throw new ApiException(ErrorCode.USER_NOT_FOUND);
    }

    // 가장 먼저 만든 게시글이 앞으로 나와야 한다.
    Pageable pageable = PageRequest.of(page, size, Sort.by("createdAt").descending());
    Page<Playlist> playlistPages = playlistRepository.findByUserId(currentUserId, pageable);

    return playlistMapper.playlistPageToPlaylistSimpleResponseDtoPage(playlistPages);
  }

  @Override
  public PlaylistEditPageResponseDto getPlaylistForEditing(Long playlistId) {
    Long currentUserId = getAuthenticatedUserIdOrThrow();

    Playlist playlist = playlistRepository.findById(playlistId)
        .orElseThrow(() -> new ApiException(ErrorCode.PLAYLIST_NOT_FOUND));

    if (!playlist.getUser().getId().equals(currentUserId)) {
      throw new ApiException(ErrorCode.UNAUTHORIZED_ACCESS);
    }

    List<PlaylistTrack> tracks = playlistTrackRepository.findByPlaylistId(playlistId);
    List<PlaylistTrackResponseDto> trackDtos = playlistTrackMapper.toDtoList(tracks);

    return PlaylistEditPageResponseDto.builder()
        .title(playlist.getTitle())
        .trackCount(tracks.size())
        .createdAt(playlist.getCreatedAt())
        .modifiedAt(playlist.getModifiedAt())
        .tracks(trackDtos)
        .thumbnailUrl(playlist.getThumbnailUrl())
        .build();

  }

  @Override
  @Transactional
  public PlaylistTrackResponseDto addTrackToPlaylist(Long playlistId,
      PlaylistTrackRequestDto requestDto) {
    Playlist playlist = playlistRepository.findById(playlistId).orElseThrow(() -> new ApiException(
        ErrorCode.PLAYLIST_NOT_FOUND));

    Long currentUserId = getAuthenticatedUserIdOrThrow();

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

    playlist.addTrack(playlistTrack);
    playlist.touch(); // 플레이리스트를 "변경된" 상태로 만들기
    PlaylistTrack savedTrack = playlistTrackRepository.save(playlistTrack);

    // 플레이리스트 저장 (수정 시간 갱신)
    playlistRepository.save(playlist);

    if (playlist.getTracks().size() == 4) {  // 4개일 때 썸네일 업데이트
      updatePlaylistThumbnail(playlistId);
    }

    return playlistTrackMapper.toDto(savedTrack);
  }

  @Override
  @Transactional
  public void removeTracksFromPlaylist(Long playlistId, List<Long> trackIds) {
    Playlist playlist = playlistRepository.findById(playlistId)
        .orElseThrow(() -> new ApiException(ErrorCode.PLAYLIST_NOT_FOUND));

    Long currentUserId = getAuthenticatedUserIdOrThrow();

    if (!playlist.getUser().getId().equals(currentUserId)) {
      throw new ApiException(ErrorCode.UNAUTHORIZED_ACCESS);
    }

    List<PlaylistTrack> tracksToRemove = playlist.getTracks().stream()
        .filter(track -> trackIds.contains(track.getId()))
        .collect(Collectors.toList());

    if (tracksToRemove.size() != trackIds.size()) {
      throw new ApiException(ErrorCode.TRACK_NOT_IN_PLAYLIST);
    }

    playlist.removeTracks(tracksToRemove);
    playlist.touch(); // 플레이리스트를 "변경된" 상태로 만들기
    playlistTrackRepository.deleteAll(tracksToRemove);

    // 플레이리스트 저장 (수정 시간 갱신)
    playlistRepository.save(playlist);

    // 트랙 삭제 후 항상 썸네일 업데이트
    updatePlaylistThumbnail(playlistId);
  }

  // PostID로 해당 플레이리스트 찾아서 반환
  public PlaylistSimpleResponseDto getPlaylistByPostId(Long postId){
    Long currentUserId = getAuthenticatedUserIdOrThrow();

    Playlist playlist = playlistRepository.findByPostsId(postId)
        .orElseThrow(() -> new ApiException(ErrorCode.PLAYLIST_NOT_FOUND));

    // 게시글 작성자 확인
    if (!playlist.getUser().getId().equals(currentUserId)) {
      throw new ApiException(ErrorCode.UNAUTHORIZED_ACCESS);
    }

    return playlistMapper.playlistToPlaylistSimpleResponseDto(playlist);
  }


  // 썸네일 업데이트를 진행하는 코드.
  @Transactional
  public void updatePlaylistThumbnail(Long playlistId) {
    Playlist playlist = playlistRepository.findById(playlistId)
        .orElseThrow(() -> new ApiException(ErrorCode.PLAYLIST_NOT_FOUND));

    List<String> albumArtUrls = playlist.getTracks().stream()
        .map(PlaylistTrack::getThumbnailUrl)
        .collect(Collectors.toList());

    try {
      String newThumbnailUrl = thumbnailGenerator.updatePlaylistThumbnail(albumArtUrls, playlist.getId());
      playlist.updateImageUrl(newThumbnailUrl);
      playlistRepository.save(playlist);
    } catch (Exception e) {
      throw new ApiException(ErrorCode.THUMBNAIL_UPDATE_FAILED);
    }
  }
}
