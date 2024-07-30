package com.chillvibe.chillvibe.domain.hashtag.controller;

import com.chillvibe.chillvibe.domain.hashtag.dto.HashtagResponseDto;
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


  /**
   * 모든 해시태그를 조회하는 엔드포인트입니다.
   *
   * @return 모든 해시태그 목록을 담은 ResponseEntity
   */
  @GetMapping("/all")
  public ResponseEntity<List<HashtagResponseDto>> getAllHashtags() {
    List<HashtagResponseDto> hashtags = hashtagService.getAllHashtags();
    return ResponseEntity.ok(hashtags);
  }

  /**
   * 인기 해시태그를 조회하는 엔드포인트입니다.
   * 기본적으로 10개의 해시태그를 반환하며, limit 쿼리 파라미터를 통해 개수를 조절할 수 있습니다.
   *
   * @param limit 조회할 해시태그 개수 (기본값: 10)
   * @return 인기 해시태그 목록을 담은 ResponseEntity
   */
  @GetMapping("/popular")
  public ResponseEntity<List<HashtagResponseDto>> getPopularHashtags(
      @RequestParam(defaultValue = "10") int limit) {
    List<HashtagResponseDto> hashtags = hashtagService.getPopularHashtags(limit);
    return ResponseEntity.ok(hashtags);
  }

  /**
   * 특정 게시글에 속한 해시태그를 조회하는 엔드포인트입니다.
   *
   * @param postId 게시글 ID
   * @return 해당 게시글의 해시태그 목록을 담은 ResponseEntity
   */
  @GetMapping(params = "postId")
  public ResponseEntity<List<HashtagResponseDto>> getHashtagsOfPost(@RequestParam Long postId) {
    List<HashtagResponseDto> hashtags = hashtagService.getHashtagsOfPost(postId);
    return ResponseEntity.ok(hashtags);
  }

  /**
   * 특정 사용자의 프로필에 설정된 해시태그를 조회하는 엔드포인트입니다.
   *
   * @param userId 사용자 ID
   * @return 해당 사용자의 프로필 해시태그 목록을 담은 ResponseEntity
   */
  @GetMapping(params = "userId")
  public ResponseEntity<List<HashtagResponseDto>> getHashtagsOfUser(@RequestParam Long userId) {
    List<HashtagResponseDto> hashtags = hashtagService.getHashtagsOfUser(userId);
    return ResponseEntity.ok(hashtags);
  }
}
