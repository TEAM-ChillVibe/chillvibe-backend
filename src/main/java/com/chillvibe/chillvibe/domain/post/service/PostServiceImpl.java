package com.chillvibe.chillvibe.domain.post.service;

import com.chillvibe.chillvibe.domain.comment.dto.CommentResponseDto;
import com.chillvibe.chillvibe.domain.comment.entity.Comment;
import com.chillvibe.chillvibe.domain.hashtag.dto.HashtagResponseDto;
import com.chillvibe.chillvibe.domain.hashtag.entity.Hashtag;
import com.chillvibe.chillvibe.domain.hashtag.entity.PostHashtag;
import com.chillvibe.chillvibe.domain.hashtag.repository.HashtagRepository;
import com.chillvibe.chillvibe.domain.hashtag.repository.PostHashtagRepository;
import com.chillvibe.chillvibe.domain.hashtag.service.HashtagService;
import com.chillvibe.chillvibe.domain.playlist.dto.PlaylistResponseDto;
import com.chillvibe.chillvibe.domain.playlist.dto.PlaylistTrackResponseDto;
import com.chillvibe.chillvibe.domain.playlist.entity.Playlist;
import com.chillvibe.chillvibe.domain.post.repository.PostLikeRepository;
import com.chillvibe.chillvibe.global.mapper.CommentMapper;
import com.chillvibe.chillvibe.global.mapper.PlaylistMapper;
import com.chillvibe.chillvibe.global.mapper.PlaylistTrackMapper;
import com.chillvibe.chillvibe.domain.playlist.repository.PlaylistRepository;
import com.chillvibe.chillvibe.domain.post.dto.PostCreateRequestDto;
import com.chillvibe.chillvibe.domain.post.dto.PostDetailResponseDto;
import com.chillvibe.chillvibe.domain.post.dto.PostListResponseDto;
import com.chillvibe.chillvibe.domain.post.dto.PostUpdateRequestDto;
import com.chillvibe.chillvibe.domain.post.entity.Post;
import com.chillvibe.chillvibe.domain.post.repository.PostRepository;
import com.chillvibe.chillvibe.domain.user.dto.UserInfoResponseDto;
import com.chillvibe.chillvibe.domain.user.dto.UserSimpleResponseDto;
import com.chillvibe.chillvibe.domain.user.entity.User;
import com.chillvibe.chillvibe.domain.user.repository.UserRepository;
import com.chillvibe.chillvibe.global.error.ErrorCode;
import com.chillvibe.chillvibe.global.error.exception.ApiException;
import com.chillvibe.chillvibe.global.jwt.util.UserUtil;
import com.chillvibe.chillvibe.global.mapper.PostMapper;
import com.chillvibe.chillvibe.global.mapper.UserMapper;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class PostServiceImpl implements PostService {

  private final PostRepository postRepository;
  private final PlaylistRepository playlistRepository;
  private final PostHashtagRepository postHashtagRepository;
  private final PostLikeRepository postLikeRepository;
  private final HashtagRepository hashtagRepository;

  private final HashtagService hashtagService;
  private final UserRepository userRepository;
  private final PostLikeService postLikeService;

  private final PlaylistMapper playlistMapper;
  private final PlaylistTrackMapper playlistTrackMapper;
  private final CommentMapper commentMapper;
  private final PostMapper postMapper;
  private final UserMapper userMapper;

  private final UserUtil userUtil;

  // 유저 검증 로직 모듈화
  private Long getAuthenticatedUserIdOrThrow() {
    Long currentUserId = userUtil.getAuthenticatedUserId();
    if (currentUserId == null) {
      throw new ApiException(ErrorCode.UNAUTHENTICATED);
    }
    return currentUserId;
  }


  /**
   * 새 게시글을 생성하고, 지정된 해시태그를 설정합니다.
   *
   * @param requestDto 게시글 생성에 필요한 정보를 담고 있는 DTO 객체. 제목, 설명, 플레이리스트 ID 및 해시태그 ID 목록을 포함합니다.
   * @return 생성된 게시글의 ID를 반환합니다.
   * @throws ApiException {UNAUTHENTICATED} - 인증된 유저가 아닐 경우
   *                      {USER_NOT_FOUND} - 유저가 데이터베이스에 존재하지 않을 경우
   *                      {PLAYLIST_NOT_FOUND} - 주어진 플레이리스트 ID로 플레이리스트를 찾을 수 없을 경우
   */
  @Transactional
  public Long createPost(PostCreateRequestDto requestDto) {
    Long currentUserId  = getAuthenticatedUserIdOrThrow();

    User user = userRepository.findById(currentUserId)
        .orElseThrow(() -> new ApiException(ErrorCode.USER_NOT_FOUND));

    long playlistId = requestDto.getPlaylistId();
    Playlist playlist = playlistRepository.findById(playlistId)
        .orElseThrow(() -> new ApiException(ErrorCode.PLAYLIST_NOT_FOUND));

    Post post = postMapper.toEntity(requestDto);
    post.setUser(user);
    post.setPlaylist(playlist);

    Post savedPost = postRepository.save(post);

    hashtagService.updateHashtagsOfPost(savedPost.getId(), requestDto.getHashtagIds());

    return savedPost.getId();
  }



  // 게시글 수정
  @Transactional
  public Long updatePost(Long postId, PostUpdateRequestDto postUpdateRequestDto) {
    Long currentUserId = getAuthenticatedUserIdOrThrow();

    Post post = postRepository.findById(postId)
        .orElseThrow(() -> new ApiException(ErrorCode.POST_NOT_FOUND));

    if (!post.getUser().getId().equals(currentUserId)) {
      throw new ApiException(ErrorCode.UNAUTHORIZED_ACCESS);
    }

    post.setTitle(postUpdateRequestDto.getTitle());
    post.setDescription(postUpdateRequestDto.getDescription());

    List<Long> hashtagIds = postUpdateRequestDto.getHashtagIds();
    hashtagService.updateHashtagsOfPost(post.getId(), hashtagIds);

    return postRepository.save(post).getId();
  }



  // 게시글 삭제
  @Transactional
  public void deletePost(Long postId) {
    // 삭제하려는 유저 정보를 가져온다.
    Long currentUserId = getAuthenticatedUserIdOrThrow();


    Post post = postRepository.findById(postId)
        .orElseThrow(() -> new ApiException(ErrorCode.POST_NOT_FOUND));

    // 해당 유저가 게시글 작성자가 아닐 경우, 에러 메세지를 발생시킨다.
    if (!post.getUser().getId().equals(currentUserId)) {
      throw new ApiException(ErrorCode.UNAUTHORIZED_ACCESS);
    }

    // 해당 게시글에 대한 모든 좋아요를 삭제하고, 총 좋아요 수를 감소시킵니다.
    int postLikeCount = postLikeRepository.countByPostId(postId);

    Set<PostHashtag> deleteHashtags = post.getPostHashtag();

    List<Long> hashtagIds = deleteHashtags.stream()
        .map(postHashtag -> postHashtag.getHashtag().getId())
        .toList();

    List<Hashtag> hashtags = hashtagRepository.findByIdIn(hashtagIds);
    for(Hashtag hashtag : hashtags){
      hashtag.setTotalLikes(hashtag.getTotalLikes() - postLikeCount);

    }

    // Hard Delete로 변경 구현.
    postRepository.delete(post);
  }

  // 전체 게시글 가져오기 - 생성일 순 & 좋아요 순
  public Page<PostListResponseDto> getPosts(String sortBy, int page, int size) {
    PageRequest pageRequest = PageRequest.of(page, size);
    Page<Post> postPage;

    if ("popular".equalsIgnoreCase(sortBy)) {
      postPage = postRepository.findByOrderByLikeCountDesc(pageRequest);
    } else {
      postPage = postRepository.findByOrderByCreatedAtDesc(pageRequest);
    }

    return postPage.map(postMapper::toPostListDto);
  }


  // 특정 게시글 조회
  public PostDetailResponseDto getPostById(Long postId) {
    // 유저 좋아요 여부를 확인
    Long currentUserId = userUtil.getAuthenticatedUserId();

    boolean userLike = postLikeRepository.existsByUserIdAndPostId(currentUserId, postId);

    Post post = postRepository.findById(postId)
        .orElseThrow(() -> new ApiException(ErrorCode.POST_NOT_FOUND));

    User user = post.getUser();
    Playlist playlist = post.getPlaylist();
    List<Comment> comments = post.getComments();

    List<HashtagResponseDto> hashtagResponseDtos = hashtagService.getHashtagsOfPost(postId);

    UserInfoResponseDto userInfoResponseDto = userMapper.userToUserInfoResponseDto(user, hashtagResponseDtos);

    List<PlaylistTrackResponseDto> playlistTrackResponseDtos = playlistTrackMapper.toDtoList(
        playlist.getTracks());

    PlaylistResponseDto playlistResponseDto = playlistMapper.playlistToPlaylistResponseDto(
        playlist, playlistTrackResponseDtos);

    List<CommentResponseDto> commentResponseDtos = comments.stream()
        .map(commentMapper::toDto)
        .collect(Collectors.toList());

    return postMapper.toPostDetailDto(post, userLike, userInfoResponseDto, playlistResponseDto,
        hashtagResponseDtos, commentResponseDtos);
  }



  // 사용자 ID로 게시글 목록 조회 (isPublic)
  public Page<PostListResponseDto> getPostsByUserId(Long userId, String sortBy, Pageable pageable) {
    // 유저 정보 조회.
    User user = userRepository.findById(userId)
        .orElseThrow(() -> new ApiException(ErrorCode.USER_NOT_FOUND));

    // 비공개 계정인 경우 빈 페이지 반환한다.
    if (!user.isPublic()) {
      return Page.empty(pageable);
    }

    Page<Post> postPage;
    if ("popular".equalsIgnoreCase(sortBy)) {
      postPage = postRepository.findByUserIdOrderByLikeCountDesc(userId, pageable);
    } else {
      postPage = postRepository.findByUserIdOrderByCreatedAtDesc(userId, pageable);
    }

    return postPage.map(postMapper::toPostListDto);
  }


  /**
   * 주어진 해시태그 ID에 해당하는 포스트를 페이지네이션하여 조회합니다.
   *
   * @param hashtagId 조회할 해시태그의 ID
   * @param pageable  페이지네이션 정보를 담고 있는 객체 (페이지 번호, 페이지 크기 등)
   * @return 주어진 해시태그에 매핑된 포스트들을 포함하는 페이지 객체, 각 포스트는 {PostRequestDto}로 변환됨
   * @throws ApiException 해당 해시태그 ID에 매핑된 포스트가 없는 경우
   */
  public Page<PostListResponseDto> getPostsByHashtagId(String sortBy, Long hashtagId,
      Pageable pageable) {

    List<PostHashtag> postHashtags = postHashtagRepository.findByHashtagId(hashtagId);

    if (postHashtags.isEmpty()) {
      return Page.empty(pageable);
    }

    List<Long> postIds = postHashtags.stream()
        .map(postHashtag -> postHashtag.getPost().getId())
        .toList();

    Page<Post> posts;
    if ("popular".equalsIgnoreCase(sortBy)) {
      posts = postRepository.findAllByIdInOrderByLikeCountDesc(postIds, pageable);
    } else {
      posts = postRepository.findAllByIdInOrderByCreatedAtDesc(postIds, pageable);
    }

    return posts.map(postMapper::toPostListDto);
  }



  public Page<PostListResponseDto> getPostSearchResults(String query, Pageable pageable) {

    // 제목에 검색어가 포함된 게시글을 대소문자 구분 없이 검색
    Page<Post> postPage = postRepository.findByTitleContainingIgnoreCaseOrderByLikeCountDesc(query,
        pageable);

    // Post 엔티티를 PostListResponseDto로 변환
    return postPage.map(postMapper::toPostListDto);
  }



  // 사용자가 좋아요한 게시글 리스트 조회 (마이페이지)
  @Transactional(readOnly = true)
  public Page<PostListResponseDto> getPostsByUserLiked(Pageable pageable) {
    List<Long> likedPostIds = postLikeService.getLikedPostIdsByUser();

    if (likedPostIds.isEmpty()) {
      return Page.empty();
    }

    Page<Post> postPage = postRepository.findAllByIdInOrderByCreatedAtDesc(likedPostIds, pageable);

    return postPage.map(postMapper::toPostListDto);
  }

  @Transactional(readOnly = true)
  public Page<PostListResponseDto> getMainPostsByLikes(int page, int size) {
    // 페이지와 크기 제한
    int limitedPage = Math.min(page, 2); // 최대 페이지 번호는 2 (0, 1, 2)
    int limitedSize = Math.min(size, 6); // 페이지 크기는 6으로 제한

    Pageable pageable = PageRequest.of(limitedPage, limitedSize);
    Page<Post> postPage = postRepository.findByOrderByLikeCountDesc(pageable);

    // Post 엔티티를 PostSimpleResponseDto로 매핑
    return postPage.map(postMapper::toPostListDto);
  }

}
