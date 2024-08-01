package com.chillvibe.chillvibe.domain.post.service;

import com.chillvibe.chillvibe.domain.hashtag.entity.PostHashtag;
import com.chillvibe.chillvibe.domain.hashtag.repository.HashtagRepository;
import com.chillvibe.chillvibe.domain.hashtag.repository.PostHashtagRepository;
import com.chillvibe.chillvibe.domain.playlist.entity.Playlist;
import com.chillvibe.chillvibe.domain.playlist.repository.PlaylistRepository;
import com.chillvibe.chillvibe.domain.post.dto.PostResponseDto;
import com.chillvibe.chillvibe.domain.post.entity.Post;
import com.chillvibe.chillvibe.domain.post.repository.PostRepository;
import com.chillvibe.chillvibe.global.error.ErrorCode;
import com.chillvibe.chillvibe.global.error.exception.ApiException;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class PostService {

  private final PostRepository postRepository;
  private final PlaylistRepository playlistRepository;
  private final HashtagRepository hashtagRepository;
  private final PostHashtagRepository postHashtagRepository;

  //생성일 순
  public Page<Post> getAllPosts(String soltBy, Pageable pageable) {
    return postRepository.findByIsDeletedFalseOrderByCreatedAtDesc(pageable);
  }

  //인기글 순
  public Page<Post> getLikePosts(String soltBy, Pageable pageable) {
    if ("like".equalsIgnoreCase(soltBy)) {
      return postRepository.findByIsDeletedFalseOrderByLikeCountDesc(pageable);
    } else {
      return postRepository.findByIsDeletedFalseOrderByCreatedAtDesc(pageable);
    }
  }

  //포스트 ID로 조회
  public Post getPostById(Long id) {
    return postRepository.findById(id)
        .orElseThrow(() -> new ApiException(ErrorCode.POST_NOT_FOUND));
  }


  //새포스트 저장
  public Post savePost(Post post) {
    return postRepository.save(post);
  }

  public Post createPost(String title, String description, String postTitleImageUrl,
      Long playlistId) {
    Playlist playlist = playlistRepository.findById(playlistId)
        .orElseThrow(() -> new ApiException(ErrorCode.POST_NOT_FOUND));

    Post post = new Post();
    post.setTitle(title);
    post.setDescription(description);
    post.setPostTitleImageUrl(postTitleImageUrl);
    post.setPlaylist(playlist);

    return postRepository.save(post);
  }

  /**
   * 주어진 해시태그 ID에 해당하는 포스트를 페이지네이션하여 조회합니다.
   *
   * @param hashtagId 조회할 해시태그의 ID
   * @param pageable  페이지네이션 정보를 담고 있는 객체 (페이지 번호, 페이지 크기 등)
   * @return 주어진 해시태그에 매핑된 포스트들을 포함하는 페이지 객체, 각 포스트는 {PostRequestDto}로 변환됨
   * @exception ApiException 해당 해시태그 ID에 매핑된 포스트가 없는 경우
   */
  public Page<PostResponseDto> getPostsByHashtagId(Long hashtagId, Pageable pageable) {
    List<PostHashtag> postHashtags = postHashtagRepository.findByHashtagId(hashtagId);

    if (postHashtags.isEmpty()) {
      throw new ApiException(ErrorCode.POST_HASHTAG_NOT_FOUND);
    }

    List<Long> postIds = postHashtags.stream()
        .map(postHashtag -> postHashtag.getPost().getId())
        .toList();

    Page<Post> posts = postRepository.findAllByIdIn(postIds, pageable);

    return posts.map(PostResponseDto::new);
  }
}