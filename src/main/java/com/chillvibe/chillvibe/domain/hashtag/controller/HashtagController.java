package com.chillvibe.chillvibe.domain.hashtag.controller;

import com.chillvibe.chillvibe.domain.hashtag.dto.HashtagDto;
import com.chillvibe.chillvibe.domain.hashtag.service.HashtagService;
import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/hashtags")
public class HashtagController {

  private final HashtagService hashtagService;

  public HashtagController(HashtagService hashtagService) {
    this.hashtagService = hashtagService;
  }

  // 모든 해시태그 조회
  @GetMapping("/all")
  public ResponseEntity<List<HashtagDto>> getAllHashtags() {
    List<HashtagDto> hashtags = hashtagService.getAllHashtags();
    return ResponseEntity.ok(hashtags);
  }

  // 인기 해시태그 조회
  @GetMapping("/popular")
  public ResponseEntity<List<HashtagDto>> getPopularHashtags(
      @RequestParam(defaultValue = "10") int limit) {
    List<HashtagDto> hashtags = hashtagService.getPopularHashtags(limit);
    return ResponseEntity.ok(hashtags);
  }

  // 특정 게시글의 해시태그 조회
  @GetMapping(params = "postId")
  public ResponseEntity<List<HashtagDto>> getHashtagsOfPost(@RequestParam Long postId) {
    List<HashtagDto> hashtags = hashtagService.getHashtagsOfPost(postId);
    return ResponseEntity.ok(hashtags);
  }

  // 특정 유저의 프로필 해시태그 조회
  @GetMapping(params = "userId")
  public ResponseEntity<List<HashtagDto>> getHashtagsOfUser(@RequestParam Long userId) {
    List<HashtagDto> hashtags = hashtagService.getHashtagsOfUser(userId);
    return ResponseEntity.ok(hashtags);
  }
}
