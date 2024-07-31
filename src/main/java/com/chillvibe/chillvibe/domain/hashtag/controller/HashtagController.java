package com.chillvibe.chillvibe.domain.hashtag.controller;

import com.chillvibe.chillvibe.domain.hashtag.dto.HashtagResponseDto;
import com.chillvibe.chillvibe.domain.hashtag.service.HashtagService;
import com.chillvibe.chillvibe.global.error.ErrorCode;
import com.chillvibe.chillvibe.global.error.exception.ApiException;
import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
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
   * 인기 해시태그를 페이지네이션을 통해 조회하는 엔드포인트입니다.
   *
   * @param page 조회할 페이지 번호 (0부터 시작)
   * @param size 페이지당 항목 수
   * @return 주어진 페이지와 크기에 해당하는 인기 해시태그의 DTO 리스트를 담은 ResponseEntity
   *
   *     1~10위의 태그를 가져오고 싶은 경우: GET /popular?page=0&size=10
   */
  @GetMapping("/popular")
  public ResponseEntity<List<HashtagResponseDto>> getPopularHashtags(
      @RequestParam(defaultValue = "0") int page,
      @RequestParam(defaultValue = "10") int size) {
    List<HashtagResponseDto> hashtags = hashtagService.getPopularHashtags(page, size);
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

  /**
   * 특정 사용자의 프로필 또는 특정 게시글에 설정될 해시태그를 변경하는 엔드포인트입니다.
   *
   * @param userId     사용자 ID (선택적, userId가 제공되면 사용자 해시태그를 업데이트)
   * @param postId     게시글 ID (선택적, postId가 제공되면 게시글 해시태그를 업데이트)
   * @param hashtagIds 해시태그 ID 리스트
   * @return 성공적으로 업데이트된 경우 HTTP 200 OK 반환
   */
  @PutMapping
  public ResponseEntity<Void> updateHashtags(
      @RequestParam(required = false) Long userId,
      @RequestParam(required = false) Long postId,
      @RequestBody List<Long> hashtagIds) {
    if (userId != null) {
      hashtagService.updateHashtagsOfUser(userId, hashtagIds);
    } else if (postId != null) {
      hashtagService.updateHashtagsOfPost(postId, hashtagIds);
    } else {
      throw new ApiException("Either userId or postId must be provided",
          ErrorCode.INVALID_INPUT_VALUE);
    }
    return ResponseEntity.ok().build();
  }
}
