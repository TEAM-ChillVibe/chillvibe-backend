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
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
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
@Tag(name = "Post", description = "게시글 API")
public class PostController {

  private final PostLikeService postLikeService;
  private final PostService postService;
  private final PlaylistService playlistService;
  private final UserUtil userUtil;


  // 전체 게시글 조회
  // 기본값은 최신순으로 조회합니다. latest & popular
  @Operation(summary = "Discover page", description = "전체 게시글 최신순 불러오는 API")
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
  @Operation(summary = "postDetail page", description = "게시글 상세 내용 불러오는 API")
  @ApiResponse
  @GetMapping("/{postId}")
  public ResponseEntity<PostDetailResponseDto> getPostById(@PathVariable Long postId) {
    PostDetailResponseDto responseDto = postService.getPostById(postId);
    return ResponseEntity.ok(responseDto);
  }

  // 특정 유저 게시글 조회
  @Operation(summary = "userId로 게시글 조회", description = "특정 userId가 작성한 게시글 불러오는 API")
  @GetMapping("/user/{userId}")
  public ResponseEntity<Page<PostListResponseDto>> getPostsByUserId(
      @PathVariable Long userId,
      @RequestParam(defaultValue = "latest") String sortBy,
      @RequestParam(defaultValue = "0") int page,
      @RequestParam(defaultValue = "10") int size) {

    Pageable pageable = PageRequest.of(page, size);
    Page<PostListResponseDto> posts = postService.getPostsByUserId(userId, sortBy, pageable);
    return ResponseEntity.ok(posts);
  }

  // 게시글 삭제
  @Operation(summary = "게시글 삭제", description = "해당 postId 게시글 삭제 API")
  @DeleteMapping("/{postId}")
  public ResponseEntity<Void> deletePost(@PathVariable Long postId) {
    postService.deletePost(postId);
    return ResponseEntity.noContent().build();
  }

  // 게시글 생성
  @Operation(summary = "게시글 생성", description = "게시글 생성으로 제목, 게시글 설명, 플레이리스트, 해쉬태그 선택 후 작성 API")
  @PostMapping
  public ResponseEntity<Long> createPost(
      @Valid @RequestBody PostCreateRequestDto requestDto) {
    Long postId = postService.createPost(requestDto);
    return ResponseEntity.ok(postId);
  }

  // 게시글 수정
  @Operation(summary = "게시글 내용 수정", description = "해당 postId의 게시글 내용 수정(제목, 게시글 설명, 해쉬태그 수정 가능 / 플레이리스트 변경 불가)API")
  @PutMapping("/{postId}")
  public ResponseEntity<Long> updatePost(
      @PathVariable Long postId,
      @RequestBody @Valid PostUpdateRequestDto postUpdateRequestDto) {

    Long updatedPostId = postService.updatePost(postId, postUpdateRequestDto);
    return ResponseEntity.ok(updatedPostId);
  }

  @Operation(summary = "게시글 좋아요", description = "게시글에 누적된 좋아요 카운트 불러오는 API")
  @GetMapping("/user/liked-posts")
  public ResponseEntity<List<Long>> getUserLikedPosts() {
    List<Long> likedPostIds = postLikeService.getLikedPostIdsByUser();
    return ResponseEntity.ok(likedPostIds);
  }

  // 좋아요 추가
  @Operation(summary = "게시글에 좋아요 추가", description = "해당 게시글 좋아요 추가 API")
  @PostMapping("/like")
  public ResponseEntity<Void> likePost(@RequestParam Long postId) {
    postLikeService.likePost(postId);
    return ResponseEntity.ok().build();
  }

  // 좋아요 취소
  @Operation(summary = "게시글에 좋아요 취소", description = "해당 게시글 좋아요 취소 API")
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
  @Operation(summary = "게시글에 해당하는 해쉬태그 조회", description = "주어진 해시태그 ID에 해당하는 포스트를 페이지네이션하여 조회하는 API")
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

  @Operation(summary = "게시글 검색", description = "게시글을 검색할 수 있는 API")
  @GetMapping("/search")
  public ResponseEntity<Page<PostListResponseDto>> getPostsSearchResults(
      @RequestParam String query,
      @RequestParam(defaultValue = "0") int page,
      @RequestParam(defaultValue = "10") int size) {
    Pageable pageable = PageRequest.of(page, size);
    Page<PostListResponseDto> resultPage = postService.getPostSearchResults(query, pageable);
    return ResponseEntity.ok(resultPage);
  }

  @Operation(summary = "user가 누른 좋아요 조회", description = "user가 게시글에 누른 좋아요를 불러오는 API")
  @GetMapping("/user/my-liked-posts")
  public ResponseEntity<Page<PostListResponseDto>> getPostsByUserLiked(Pageable pageable) {
    Page<PostListResponseDto> likedPostsPage = postService.getPostsByUserLiked(pageable);
    return ResponseEntity.ok(likedPostsPage);
  }

  @Operation(summary = "메인페이지 표시용 전체 게시글 조회", description = "메인 페이지에 전체 게시글을 불러오는 API")
  @GetMapping("/main")
  public ResponseEntity<List<PostSimpleResponseDto>> getMainpagePosts() {
    List<PostSimpleResponseDto> mainPosts = postService.getMainPostsByLikes();
    return ResponseEntity.ok(mainPosts);
  }
}
