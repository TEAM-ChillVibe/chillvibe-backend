package com.chillvibe.chillvibe.domain.hashtag.service;

import com.chillvibe.chillvibe.domain.hashtag.dto.HashtagDto;
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
import java.util.List;
import java.util.stream.Collectors;
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

  // 모든 해시태그 조회
  public List<HashtagDto> getAllHashtags() {
    if (hashtagRepository.count() == 0) {
      throw new ApiException(ErrorCode.HASHTAG_NOT_FOUND);
    }
    return hashtagRepository.findAll().stream()
        .map(Hashtag::toDto)
        .collect(Collectors.toList());
  }

  // 인기 해시태그 조회
  public List<HashtagDto> getPopularHashtags(int limit) {
    if (hashtagRepository.count() == 0) {
      throw new ApiException(ErrorCode.HASHTAG_NOT_FOUND);
    }
    List<Hashtag> popularHashtags = hashtagRepository.findTopNByOrderByTotalLikesDesc(limit);
    return popularHashtags.stream()
        .map(Hashtag::toDto)
        .toList();
  }

  // 특정 게시글의 해시태그 조회
  public List<HashtagDto> getHashtagsOfPost(Long postId) {
    List<PostHashtag> postHashtags = postHashtagRepository.findByPostId(postId);
    if (postHashtags.isEmpty()) {
      throw new ApiException(ErrorCode.POST_HASHTAG_NOT_FOUND);
    }
    return postHashtags.stream()
        .map(postHashtag -> postHashtag.getHashtag().toDto())
        .toList();
  }

  // 특정 유저의 해시태그 조회
  public List<HashtagDto> getHashtagsOfUser(Long userId) {
    List<UserHashtag> userHashtags = userHashtagRepository.findByUserId(userId);
    if (userHashtags.isEmpty()) {
      throw new ApiException(ErrorCode.USER_HASHTAG_NOT_FOUND);
    }
    return userHashtags.stream()
        .map(userHashtag -> userHashtag.getHashtag().toDto())
        .toList();
  }

  // 인기 해시태그 계산 (게시글 좋아요 수를 통해 누적)
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
