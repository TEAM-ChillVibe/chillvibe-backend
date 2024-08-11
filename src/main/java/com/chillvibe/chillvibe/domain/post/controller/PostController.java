package com.chillvibe.chillvibe.domain.post.controller;

import com.chillvibe.chillvibe.domain.playlist.service.PlaylistService;
import com.chillvibe.chillvibe.domain.post.dto.PostCreateRequestDto;
import com.chillvibe.chillvibe.domain.post.dto.PostDetailResponseDto;
import com.chillvibe.chillvibe.domain.post.dto.PostListResponseDto;
import com.chillvibe.chillvibe.domain.post.dto.PostSimpleResponseDto;
import com.chillvibe.chillvibe.domain.post.dto.PostUpdateRequestDto;
import com.chillvibe.chillvibe.domain.post.service.PostLikeService;
import com.chillvibe.chillvibe.domain.post.service.PostService;
import com.chillvibe.chillvibe.global.jwt.util.UserUtil;
import jakarta.validation.Valid;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RequestMapping("/api/posts")
@RestController
public class PostController {

  private final PostLikeService postLikeService;
  private final PostService postService;
  private final PlaylistService playlistService;
  private final UserUtil userUtil;


  // 전체 게시글 조회
  // 기본값은 최신순으로 조회합니다. latest & popular
  @GetMapping
  public ResponseEntity<Page<PostListResponseDto>> getAllPosts(
      @RequestParam(defaultValue = "latest") String sortBy,
      @RequestParam(defaultValue = "0") int page,
      @RequestParam(defaultValue = "10") int size
  ) {
    Page<PostListResponseDto> posts = postService.getPosts(sortBy, page, size);
    return ResponseEntity.ok(posts);
  }

  // 특정 게시글 상세 조회
  @GetMapping("/{postId}")
  public ResponseEntity<PostDetailResponseDto> getPostById(@PathVariable Long postId) {
    PostDetailResponseDto responseDto = postService.getPostById(postId);
    return ResponseEntity.ok(responseDto);
  }

  // 특정 유저 게시글 조회
  @GetMapping("/user/{userId}")
  public ResponseEntity<Page<PostListResponseDto>> getPostsByUserId(
      @PathVariable Long userId,
      @RequestParam(defaultValue = "0") int page,
      @RequestParam(defaultValue = "10") int size) {

    Pageable pageable = PageRequest.of(page, size, Sort.by("createdAt").descending());
    Page<PostListResponseDto> posts = postService.getPostsByUserId(userId, pageable);
    return ResponseEntity.ok(posts);
  }

  // 게시글 삭제
  @DeleteMapping("/{postId}")
  public ResponseEntity<Void> deletePost(@PathVariable Long postId) {
    postService.deletePost(postId);
    return ResponseEntity.noContent().build();
  }

  // 게시글 생성
  @PostMapping
  public ResponseEntity<Long> createPost(
      @Valid @RequestBody PostCreateRequestDto requestDto) {
    Long postId = postService.createPost(requestDto);
    return ResponseEntity.ok(postId);
  }

  // 게시글 수정
  @PutMapping("/{postId}")
  public ResponseEntity<Long> updatePost(
      @PathVariable Long postId,
      @RequestBody PostUpdateRequestDto postUpdateRequestDto) {

    Long updatedPostId = postService.updatePost(postId, postUpdateRequestDto);
    return ResponseEntity.ok(updatedPostId);
  }

  @GetMapping("/user/liked-posts")
  public ResponseEntity<List<Long>> getUserLikedPosts() {
    List<Long> likedPostIds = postLikeService.getLikedPostIdsByUser();
    return ResponseEntity.ok(likedPostIds);
  }

  // 좋아요 추가
  @PostMapping("/like")
  public ResponseEntity<Void> likePost(@RequestParam Long postId) {
    postLikeService.likePost(postId);
    return ResponseEntity.ok().build();
  }

  // 좋아요 취소
  @DeleteMapping("/like")
  public ResponseEntity<Void> unlikePost(@RequestParam Long postId) {
    postLikeService.unlikePost(postId);
    return ResponseEntity.ok().build();
  }

  /**
   * 주어진 해시태그 ID에 해당하는 포스트를 페이지네이션하여 조회하는 엔드포인트입니다.
   *
   * @param hashtagId 조회할 해시태그의 ID
   * @param page      페이지 번호 (기본값: 0)
   * @param size      페이지 크기 (기본값: 10)
   * @return 주어진 해시태그에 매핑된 포스트들을 포함하는 Page 객체, 각 포스트는 PostRequestDto로 변환됨
   */
  @GetMapping("/hashtags")
  public ResponseEntity<Page<PostListResponseDto>> getPostsByHashtagId(
      @RequestParam(defaultValue = "latest") String sortBy,
      @RequestParam Long hashtagId,
      @RequestParam(defaultValue = "0") int page,
      @RequestParam(defaultValue = "10") int size) {
    Pageable pageable = PageRequest.of(page, size);
    Page<PostListResponseDto> resultPage = postService.getPostsByHashtagId(sortBy, hashtagId,
        pageable);
    return ResponseEntity.ok(resultPage);
  }

  @GetMapping("/search")
  public ResponseEntity<Page<PostListResponseDto>> getPostsSearchResults(
      @RequestParam String query,
      @RequestParam(defaultValue = "0") int page,
      @RequestParam(defaultValue = "10") int size) {
    Pageable pageable = PageRequest.of(page, size);
    Page<PostListResponseDto> resultPage = postService.getPostSearchResults(query, pageable);
    return ResponseEntity.ok(resultPage);
  }

  @GetMapping("/user/my-liked-posts")
  public ResponseEntity<Page<PostListResponseDto>> getPostsByUserLiked(Pageable pageable) {
    Page<PostListResponseDto> likedPostsPage = postService.getPostsByUserLiked(pageable);
    return ResponseEntity.ok(likedPostsPage);
  }

  @GetMapping("/main")
  public ResponseEntity<List<PostSimpleResponseDto>> getMainpagePosts() {
    List<PostSimpleResponseDto> mainPosts = postService.getMainPostsByLikes();
    return ResponseEntity.ok(mainPosts);
  }
}
