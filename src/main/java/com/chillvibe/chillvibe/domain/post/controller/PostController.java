package com.chillvibe.chillvibe.domain.post.controller;

import com.chillvibe.chillvibe.domain.playlist.entity.Playlist;
import com.chillvibe.chillvibe.domain.post.service.PostLikeService;
import com.chillvibe.chillvibe.domain.post.service.PostService;
import com.chillvibe.chillvibe.global.s3.service.S3Uploader;
import io.jsonwebtoken.io.IOException;
import java.util.ArrayList;
import java.util.List;
import lombok.SneakyThrows;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import lombok.RequiredArgsConstructor;

import com.chillvibe.chillvibe.domain.post.entity.Post;
import com.chillvibe.chillvibe.domain.post.repository.PostRepository;
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
      @RequestParam(defaultValue = "50") int size,
      @RequestParam(defaultValue = "createdAt") String soltBy){

    Pageable pageable = PageRequest.of(page, size);
    Page<Post> resultPage = postService.getAllPosts(soltBy, pageable);
    return ResponseEntity.ok(resultPage);
  }

  @GetMapping("/{postId}")
  public ResponseEntity<Post> getPostById(@PathVariable Long id){
    return ResponseEntity.ok(postService.getPostById(id));
  }

  @SneakyThrows
  @PostMapping
  public ResponseEntity<Post> createPost(
      @RequestParam("title") String title,
      @RequestParam("description") String  description,
      @RequestParam("postTitleImage")MultipartFile postTitleImage,
      @RequestParam("postImage")MultipartFile[] postImages,
      @RequestParam("playlistId") Long playlistId) throws IOException {

    String postTitleImageUrl = s3Uploader.upload(postTitleImage, "post-title-image");
    List<String> postImageUrl = new ArrayList<>();
    for (MultipartFile image : postImages) {
      postImageUrl.add(s3Uploader.upload(image, "post-image"));
    }

    Post post = postService.createPost(title, description, postTitleImageUrl, postImageUrl, playlistId);
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
}
