package com.chillvibe.chillvibe.domain.post.controller;

import com.chillvibe.chillvibe.domain.post.dto.PostListResponseDto;
import com.chillvibe.chillvibe.domain.post.entity.Post;
import com.chillvibe.chillvibe.domain.post.service.PostLikeService;
import com.chillvibe.chillvibe.domain.post.service.PostService;
import com.chillvibe.chillvibe.global.s3.service.S3Uploader;
import io.jsonwebtoken.io.IOException;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RequiredArgsConstructor
@RequestMapping("/posts")
@RestController
public class PostController {

  private final PostLikeService postLikeService;
  private final PostService postService;
  private final S3Uploader s3Uploader;

  @GetMapping
  public ResponseEntity<Page<Post>> getAllPosts(
      @RequestParam(defaultValue = "0") int page,
      @RequestParam(defaultValue = "10") int size,
      @RequestParam(defaultValue = "createdAt") String soltBy) {

    Pageable pageable = PageRequest.of(page, size);
    Page<Post> resultPage = postService.getAllPosts(soltBy, pageable);
    return ResponseEntity.ok(resultPage);
  }

  @GetMapping("/{postId}")
  public ResponseEntity<Post> getPostById(@PathVariable Long id) {
    return ResponseEntity.ok(postService.getPostById(id));
  }

  /**
   * 새 게시글을 생성합니다.
   *
   * @param title          게시글 제목
   * @param description    게시글 설명
   * @param postTitleImage 게시글 타이틀 이미지 파일
   * @param playlistId     플레이리스트 ID
   * @param hashtagIds     해시태그 ID 리스트
   * @return 생성된 게시글의 DTO
   * @exception IOException S3 업로드 또는 파일 처리 중 오류 발생
   */
  @SneakyThrows
  @PostMapping
  public ResponseEntity<PostListResponseDto> createPost(
      @RequestParam("title") String title,
      @RequestParam("description") String description,
      @RequestParam("postTitleImage") MultipartFile postTitleImage,
      @RequestParam("playlistId") Long playlistId,
      @RequestParam("hashtagIds") List<Long> hashtagIds) throws IOException {

    String postTitleImageUrl = s3Uploader.upload(postTitleImage, "post-title-image");

    PostListResponseDto postResponseDto = postService.createPost(title, description,
        postTitleImageUrl,
        playlistId, hashtagIds);

    return ResponseEntity.ok(postResponseDto);
  }


  //좋아요 추가
  @PostMapping("/{postId}/like")
  public ResponseEntity<Void> likePost(@RequestParam Long userId, @PathVariable Long postId) {
    postLikeService.likePost(userId, postId);
    return ResponseEntity.ok().build();
  }

  //좋아요 취소
  @PostMapping("/{postId}/unlike")
  public ResponseEntity<Void> unlikePost(@RequestParam Long userId, @PathVariable Long postId) {
    postLikeService.unlikePost(userId, postId);
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
      @RequestParam Long hashtagId,
      @RequestParam(defaultValue = "0") int page,
      @RequestParam(defaultValue = "10") int size) {
    Pageable pageable = PageRequest.of(page, size);
    Page<PostListResponseDto> resultPage = postService.getPostsByHashtagId(hashtagId, pageable);
    return ResponseEntity.ok(resultPage);
  }
}
