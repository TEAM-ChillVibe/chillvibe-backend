package com.chillvibe.chillvibe.domain.hashtag.service;

import com.chillvibe.chillvibe.domain.hashtag.dto.HashtagResponseDto;
import com.chillvibe.chillvibe.domain.hashtag.entity.Hashtag;
import com.chillvibe.chillvibe.domain.hashtag.entity.PostHashtag;
import com.chillvibe.chillvibe.domain.hashtag.entity.UserHashtag;
import com.chillvibe.chillvibe.domain.hashtag.repository.HashtagRepository;
import com.chillvibe.chillvibe.domain.hashtag.repository.PostHashtagRepository;
import com.chillvibe.chillvibe.domain.hashtag.repository.UserHashtagRepository;
import com.chillvibe.chillvibe.domain.post.repository.PostLikeRepository;
import com.chillvibe.chillvibe.domain.post.repository.PostRepository;
import com.chillvibe.chillvibe.global.error.ErrorCode;
import com.chillvibe.chillvibe.global.error.exception.ApiException;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class HashtagService {

  private final HashtagRepository hashtagRepository;
  private final PostHashtagRepository postHashtagRepository;
  private final UserHashtagRepository userHashtagRepository;
  private final PostLikeRepository postLikeRepository;

  public HashtagService(HashtagRepository hashtagRepository, PostRepository postRepository,
      PostHashtagRepository postHashtagRepository, UserHashtagRepository userHashtagRepository,
      PostLikeRepository postLikeRepository) {
    this.hashtagRepository = hashtagRepository;
    this.postHashtagRepository = postHashtagRepository;
    this.userHashtagRepository = userHashtagRepository;
    this.postLikeRepository = postLikeRepository;
  }

  /**
   * 시스템에 존재하는 모든 해시태그를 조회합니다.
   *
   * @return 모든 해시태그를 나타내는 HashtagDto 객체의 리스트
   * @exception ApiException 해시태그가 하나도 존재하지 않을 경우
   */
  public List<HashtagResponseDto> getAllHashtags() {
    if (hashtagRepository.count() == 0) {
      throw new ApiException(ErrorCode.HASHTAG_NOT_FOUND);
    }
    return hashtagRepository.findAll().stream()
        .map(Hashtag::toDto)
        .collect(Collectors.toList());
  }

  /**
   * 총 좋아요 수를 기준으로 인기 있는 해시태그를 조회합니다.
   *
   * @param page 현재 페이지 번호 (0부터 시작)
   * @param size 페이지당 항목 수
   * @return 주어진 페이지와 크기에 해당하는 인기 해시태그의 목록을 포함하는 HashtagDto 리스트
   * @exception ApiException 해시태그가 하나도 존재하지 않을 경우
   */
  public List<HashtagResponseDto> getPopularHashtags(int page, int size) {
    // 페이지 번호와 크기 값이 유효한지 검증
    if (page < 0 || size <= 0) {
      throw new ApiException("page or size must be positive", ErrorCode.POSITIVE_VALUE_REQUIRED);
    }

    Pageable pageable = PageRequest.of(page, size);
    Page<Hashtag> popularHashtags = hashtagRepository.findTopByOrderByTotalLikesDESC(pageable);

    // 조회 결과가 없는 경우 빈 리스트 반환
    if (popularHashtags.isEmpty()) {
      return Collections.emptyList();
    }

    return popularHashtags.stream()
        .map(Hashtag::toDto)
        .toList();
  }

  /**
   * 특정 게시글에 속한 해시태그를 조회합니다.
   *
   * @param postId 게시글의 ID
   * @return 게시글의 해시태그를 나타내는 HashtagDto 객체의 리스트
   * @exception ApiException 게시글에 해시태그가 하나도 존재하지 않을 경우
   */
  public List<HashtagResponseDto> getHashtagsOfPost(Long postId) {
    List<PostHashtag> postHashtags = postHashtagRepository.findByPostId(postId);
    if (postHashtags.isEmpty()) {
      throw new ApiException(ErrorCode.POST_HASHTAG_NOT_FOUND);
    }
    return postHashtags.stream()
        .map(postHashtag -> postHashtag.getHashtag().toDto())
        .toList();
  }

  /**
   * 특정 사용자와 관련된 해시태그를 조회합니다.
   *
   * @param userId 사용자의 ID
   * @return 사용자의 해시태그를 나타내는 HashtagDto 객체의 리스트
   * @exception ApiException 사용자와 관련된 해시태그가 하나도 존재하지 않을 경우
   */
  public List<HashtagResponseDto> getHashtagsOfUser(Long userId) {
    List<UserHashtag> userHashtags = userHashtagRepository.findByUserId(userId);
    if (userHashtags.isEmpty()) {
      throw new ApiException(ErrorCode.USER_HASHTAG_NOT_FOUND);
    }
    return userHashtags.stream()
        .map(userHashtag -> userHashtag.getHashtag().toDto())
        .toList();
  }


  /**
   * 게시글의 좋아요 수를 기반으로 해당 게시글에 속한 해시태그의 총 좋아요 수를 증가시킵니다.
   *
   * @param postId 좋아요 수가 증가한 게시글의 ID
   */
  @Transactional
  public void increaseHashtagLikes(Long postId) {
    int likeCount = postLikeRepository.countByPostId(postId);

    // 게시글에 포함된 모든 해시태그에 게시글의 좋아요 수를 반영
    List<PostHashtag> postHashtags = postHashtagRepository.findByPostId(postId);
    for (PostHashtag postHashtag : postHashtags) {
      Hashtag hashtag = postHashtag.getHashtag();
      hashtag.increaseTotalLikes(likeCount);
      hashtagRepository.save(hashtag);
    }
  }

  /**
   * 게시글의 좋아요 수를 기반으로 해당 게시글에 속한 해시태그의 총 좋아요 수를 감소시킵니다.
   *
   * @param postId 좋아요 수가 감소한 게시글의 ID
   */
  @Transactional
  public void decreaseHashtagLikes(Long postId) {
    int likeCount = postLikeRepository.countByPostId(postId);
    List<PostHashtag> postHashtags = postHashtagRepository.findByPostId(postId);
    for (PostHashtag postHashtag : postHashtags) {
      Hashtag hashtag = postHashtag.getHashtag();
      hashtag.decreaseTotalLikes(likeCount);
      hashtagRepository.save(hashtag);
    }
  }
}
