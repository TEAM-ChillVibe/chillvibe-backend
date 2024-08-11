package com.chillvibe.chillvibe.domain.hashtag.service;

import com.chillvibe.chillvibe.domain.hashtag.dto.HashtagResponseDto;
import com.chillvibe.chillvibe.domain.hashtag.entity.Hashtag;
import com.chillvibe.chillvibe.domain.hashtag.entity.PostHashtag;
import com.chillvibe.chillvibe.domain.hashtag.entity.UserHashtag;
import com.chillvibe.chillvibe.domain.hashtag.repository.HashtagRepository;
import com.chillvibe.chillvibe.domain.hashtag.repository.PostHashtagRepository;
import com.chillvibe.chillvibe.domain.hashtag.repository.UserHashtagRepository;
import com.chillvibe.chillvibe.domain.post.entity.Post;
import com.chillvibe.chillvibe.domain.post.repository.PostRepository;
import com.chillvibe.chillvibe.domain.user.entity.User;
import com.chillvibe.chillvibe.domain.user.repository.UserRepository;
import com.chillvibe.chillvibe.global.error.ErrorCode;
import com.chillvibe.chillvibe.global.error.exception.ApiException;
import com.chillvibe.chillvibe.global.jwt.util.UserUtil;
import com.chillvibe.chillvibe.global.mapper.HashtagMapper;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class HashtagService {

  private final HashtagRepository hashtagRepository;
  private final PostHashtagRepository postHashtagRepository;
  private final UserHashtagRepository userHashtagRepository;
  private final PostRepository postRepository;
  public final UserUtil userUtil;
  private final UserRepository userRepository;
  private final HashtagMapper hashtagMapper;

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
    // Mapper / 추가 코드
    List<Hashtag> hashtags = hashtagRepository.findAll();
    return hashtagMapper.hashtagsToHashtagResponseDtos(hashtags);

    // Mapper / 기존 코드
//    return hashtagRepository.findAll().stream()
//        .map(Hashtag::toDto)
//        .collect(Collectors.toList());
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
    Page<Hashtag> popularHashtags = hashtagRepository.findTopByOrderByTotalLikesDescRandom(
        pageable);

    // 조회 결과가 없는 경우 빈 리스트 반환
    if (popularHashtags.isEmpty()) {
      return Collections.emptyList();
    }

    // Mapper / 추가 코드
    return hashtagMapper.hashtagsToHashtagResponseDtos(popularHashtags.getContent());
    // Mapper / 기존 코드
//    return popularHashtags.stream()
//        .map(Hashtag::toDto)
//        .toList();
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
      return Collections.emptyList();
    }
    // Mapper / 추가 코드
    List<Hashtag> hashtags = postHashtags.stream()
        .map(PostHashtag::getHashtag)
        .toList();
    return hashtagMapper.hashtagsToHashtagResponseDtos(hashtags);
    // Mapper / 기존 코드
//    return postHashtags.stream()
//        .map(postHashtag -> postHashtag.getHashtag().toDto())
//        .toList();
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
      return Collections.emptyList();
    }
    // Mapper / 추가 코드
    List<Hashtag> hashtags = userHashtags.stream()
        .map(UserHashtag::getHashtag)
        .toList();
    return hashtagMapper.hashtagsToHashtagResponseDtos(hashtags);

    //
    // Mapper / 기존 코드
//    return userHashtags.stream()
//        .map(userHashtag -> userHashtag.getHashtag().toDto())
//        .toList();
  }

  /**
   * 게시글의 좋아요 수를 기반으로 해당 게시글에 속한 해시태그의 총 좋아요 수를 변화시킵니다.
   *
   * @param postId   좋아요 수가 변화한 게시글의 ID
   * @param increase 좋아요 수를 증가시킬지 감소시킬지 결정하는 플래그.
   *                 `true`일 경우 총 좋아요 수를 증가시키고, `false`일 경우 감소시킵니다.
   */
  public void adjustHashtagLikes(Long postId, boolean increase) {
    List<PostHashtag> postHashtags = postHashtagRepository.findByPostId(postId);

    List<Hashtag> hashtagsToUpdate = postHashtags.stream()
        .map(postHashtag -> {
          Hashtag hashtag = postHashtag.getHashtag();
          if (increase) {
            hashtag.increaseTotalLikes();
          } else {
            hashtag.decreaseTotalLikes();
          }
          return hashtag;
        })
        .toList();

    hashtagRepository.saveAll(hashtagsToUpdate);
  }


  /**
   * 특정 사용자의 프로필에 설정될 해시태그를 설정(변경)합니다.
   *
   * @param hashtagIds 해시태그 ID list
   * @exception ApiException UNAUTHENTICATED 인증되지 않은 사용자일 경우
   */
  @Transactional
  public void updateHashtagsOfUser(List<Long> hashtagIds) {
    Long userId = userUtil.getAuthenticatedUserId();
    if (userId == null) {
      throw new ApiException(ErrorCode.UNAUTHENTICATED);
    }

    userHashtagRepository.deleteByUserId(userId);

    User user = userRepository.findById(userId)
        .orElseThrow(() -> new ApiException(ErrorCode.USER_NOT_FOUND));
    List<Hashtag> hashtags = hashtagRepository.findAllById(hashtagIds);
    List<UserHashtag> userHashtags = hashtags.stream()
        .map(hashtag -> new UserHashtag(user, hashtag))
        .toList();

    userHashtagRepository.saveAll(userHashtags);
  }

  /**
   * 특정 게시글에 설정될 해시태그를 설정(변경)합니다.
   *
   * @param postId     게시글 ID
   * @param hashtagIds 해시태그 ID list
   */
  @Transactional
  public void updateHashtagsOfPost(Long postId, List<Long> hashtagIds) {
    postHashtagRepository.deleteByPostId(postId);

    Post post = postRepository.findById(postId)
        .orElseThrow(() -> new ApiException(ErrorCode.POST_NOT_FOUND));

    for (Long hashtagId : hashtagIds) {
      Hashtag hashtag = hashtagRepository.findById(hashtagId)
          .orElseThrow(() -> new ApiException(ErrorCode.HASHTAG_NOT_FOUND));
      PostHashtag newPostHashtag = new PostHashtag(post, hashtag);
      postHashtagRepository.save(newPostHashtag);
    }
  }
}
