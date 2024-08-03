package com.chillvibe.chillvibe.domain.post.service;

import com.chillvibe.chillvibe.domain.hashtag.entity.PostHashtag;
import com.chillvibe.chillvibe.domain.hashtag.repository.PostHashtagRepository;
import com.chillvibe.chillvibe.domain.hashtag.service.HashtagService;
import com.chillvibe.chillvibe.domain.playlist.entity.Playlist;
import com.chillvibe.chillvibe.domain.playlist.repository.PlaylistRepository;
import com.chillvibe.chillvibe.domain.post.dto.PostCreateRequestDto;
import com.chillvibe.chillvibe.domain.post.dto.PostListResponseDto;
import com.chillvibe.chillvibe.domain.post.dto.PostResponseDto;
import com.chillvibe.chillvibe.domain.post.entity.Post;
import com.chillvibe.chillvibe.domain.post.repository.PostRepository;
import com.chillvibe.chillvibe.domain.user.entity.User;
import com.chillvibe.chillvibe.domain.user.repository.UserRepository;
import com.chillvibe.chillvibe.global.error.ErrorCode;
import com.chillvibe.chillvibe.global.error.exception.ApiException;
import com.chillvibe.chillvibe.global.jwt.util.UserUtil;
import java.util.List;
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
  private final HashtagService hashtagService;
  private final UserRepository userRepository;
  private final UserUtil userUtil;

  // 전체 게시글 가져오기 - 생성일 순 & 좋아요 순
  public Page<PostListResponseDto> getPosts(String sortBy, int page, int size) {
    PageRequest pageRequest = PageRequest.of(page, size);
    Page<Post> postPage;

    if ("popular".equalsIgnoreCase(sortBy)) {
      postPage = postRepository.findByIsDeletedFalseOrderByLikeCountDesc(pageRequest);
    } else {
      postPage = postRepository.findByIsDeletedFalseOrderByCreatedAtDesc(pageRequest);
    }

    return postPage.map(PostListResponseDto::new);
  }


  //포스트 ID로 조회
  public Post getPostById(Long id) {
    return postRepository.findById(id)
        .orElseThrow(() -> new ApiException(ErrorCode.POST_NOT_FOUND));
  }

  // 사용자 ID로 게시글 목록 조회
  public Page<PostResponseDto> getPostsByUserId(Long userId, Pageable pageable) {
    Page<Post> posts = postRepository.findByUserIdAndIsDeletedFalse(userId, pageable);
    return posts.map(PostResponseDto::new);
  }


  // 새포스트 저장
  public Post savePost(Post post) {
    return postRepository.save(post);
  }


  // 포스트 삭제
  public void deletePost(Long postId) {
    Post post = postRepository.findById(postId)
        .orElseThrow(() -> new ApiException(ErrorCode.POST_NOT_FOUND));
    post.setDeleted(true);
    postRepository.save(post);
  }

  /**
   * 새 게시글을 생성하고, 지정된 해시태그를 설정합니다.
   *
   * @param requestDto 게시글 생성에 필요한 정보를 담고 있는 DTO 객체.
   *                   제목, 설명, 플레이리스트 ID 및 해시태그 ID 목록을 포함합니다.
   * @return 생성된 게시글의 정보를 담고 있는 {PostListResponseDto} 객체를 반환합니다.
   * @exception ApiException {UNAUTHENTICATED} - 인증된 유저가 아닐 경우
   *                         {USER_NOT_FOUND} - 유저가 데이터베이스에 존재하지 않을 경우
   *                         {PLAYLIST_NOT_FOUND} - 주어진 플레이리스트 ID로 플레이리스트를 찾을 수 없을 경우
   */
  @Transactional
  public PostListResponseDto createPost(PostCreateRequestDto requestDto) {
    Long userId = userUtil.getAuthenticatedUserId();
    if (userId == null) {
      throw new ApiException(ErrorCode.UNAUTHENTICATED); // 인증된 유저가 아닙니다.
    }

    // 유저 받아오기.
    User user = userRepository.findById(userId)
        .orElseThrow(() -> new ApiException(ErrorCode.USER_NOT_FOUND));

    long playlistId = requestDto.getPlaylistId();

    Playlist playlist = playlistRepository.findById(playlistId)
        .orElseThrow(() -> new ApiException(ErrorCode.PLAYLIST_NOT_FOUND));

    Post post = new Post();
    post.setTitle(requestDto.getTitle());
    post.setDescription(requestDto.getDescription());
    post.setPostTitleImageUrl("default image");
    post.setUser(user);
    post.setPlaylist(playlist);
    post.setDeleted(false);
    post.setLikeCount(0);

    Post savedPost = postRepository.save(post);

    List<Long> hashtagIds = requestDto.getHashtagIds();
    hashtagService.updateHashtagsOfPost(savedPost.getId(), hashtagIds);

    return new PostListResponseDto(savedPost);
  }

  //게시글 수정
  @Transactional
  public PostResponseDto updatePost(Long postId, String title, String description,
      String postTitleImageUrl, Long playlistId, List<Long> hashtagIds) {
    Post post = postRepository.findById(postId)
        .orElseThrow(() -> new ApiException(ErrorCode.POST_NOT_FOUND));

    post.setTitle(title);
    post.setDescription(description);
    post.setPostTitleImageUrl(postTitleImageUrl);

    if (playlistId != null) {
      Playlist playlist = playlistRepository.findById(playlistId)
          .orElseThrow(() -> new ApiException(ErrorCode.PLAYLIST_NOT_FOUND));
      post.setPlaylist(playlist);
    }

    Post updatedPost = postRepository.save(post);

    if (hashtagIds != null && !hashtagIds.isEmpty()) {
      hashtagService.updateHashtagsOfPost(updatedPost.getId(), hashtagIds);
    }

    return new PostResponseDto(updatedPost);
  }


  /**
   * 주어진 해시태그 ID에 해당하는 포스트를 페이지네이션하여 조회합니다.
   *
   * @param hashtagId 조회할 해시태그의 ID
   * @param pageable  페이지네이션 정보를 담고 있는 객체 (페이지 번호, 페이지 크기 등)
   * @return 주어진 해시태그에 매핑된 포스트들을 포함하는 페이지 객체, 각 포스트는 {PostRequestDto}로 변환됨
   * @exception ApiException 해당 해시태그 ID에 매핑된 포스트가 없는 경우
   */
  public Page<PostListResponseDto> getPostsByHashtagId(Long hashtagId, Pageable pageable) {
    List<PostHashtag> postHashtags = postHashtagRepository.findByHashtagId(hashtagId);

    if (postHashtags.isEmpty()) {
      throw new ApiException(ErrorCode.POST_HASHTAG_NOT_FOUND);
    }

    List<Long> postIds = postHashtags.stream()
        .map(postHashtag -> postHashtag.getPost().getId())
        .toList();

    Page<Post> posts = postRepository.findAllByIdIn(postIds, pageable);

    return posts.map(PostListResponseDto::new);
  }
}