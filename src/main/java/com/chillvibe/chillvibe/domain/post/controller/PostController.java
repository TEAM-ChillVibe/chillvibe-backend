package com.chillvibe.chillvibe.domain.post.controller;

import com.chillvibe.chillvibe.domain.post.dto.PostResponseDto;
import com.chillvibe.chillvibe.domain.post.entity.Post;
import com.chillvibe.chillvibe.domain.post.service.PostLikeService;
import com.chillvibe.chillvibe.domain.post.service.PostService;
import com.chillvibe.chillvibe.global.s3.service.S3Uploader;
import io.jsonwebtoken.io.IOException;
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

  @SneakyThrows
  @PostMapping
  public ResponseEntity<Post> createPost(
      @RequestParam("title") String title,
      @RequestParam("description") String description,
      @RequestParam("postTitleImage") MultipartFile postTitleImage,
      @RequestParam("playlistId") Long playlistId) throws IOException {

    String postTitleImageUrl = s3Uploader.upload(postTitleImage, "post-title-image");

    Post post = postService.createPost(title, description, postTitleImageUrl, playlistId);
    return ResponseEntity.ok(post);
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

  @GetMapping("/hashtags")
  public ResponseEntity<Page<PostResponseDto>> getPostsByHashtagId(
      @RequestParam Long hashtagId,
      @RequestParam(defaultValue = "0") int page,
      @RequestParam(defaultValue = "10") int size) {
    Pageable pageable = PageRequest.of(page, size);
    Page<PostResponseDto> resultPage = postService.getPostsByHashtagId(hashtagId, pageable);
    return ResponseEntity.ok(resultPage);
  }
}
