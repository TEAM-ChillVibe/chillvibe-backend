package com.chillvibe.chillvibe.domain.post.service;

import com.chillvibe.chillvibe.domain.hashtag.service.HashtagService;
import com.chillvibe.chillvibe.domain.post.entity.Post;
import com.chillvibe.chillvibe.domain.post.entity.PostLike;
import com.chillvibe.chillvibe.domain.post.repository.PostLikeRepository;
import com.chillvibe.chillvibe.domain.post.repository.PostRepository;
import com.chillvibe.chillvibe.domain.user.entity.User;
import com.chillvibe.chillvibe.domain.user.repository.UserRepository;
import com.chillvibe.chillvibe.global.error.ErrorCode;
import com.chillvibe.chillvibe.global.error.exception.ApiException;
import com.chillvibe.chillvibe.global.jwt.util.UserUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class PostLikeServiceImpl implements PostLikeService {


  private final PostLikeRepository postLikeRepository;
  private final PostRepository postRepository;
  private final UserRepository userRepository;
  private final HashtagService hashtagService;
  private final UserUtil userUtil;

  //좋아요 누르기
  @Transactional
  public void likePost(Long postId) {
    Long userId = userUtil.getAuthenticatedUserId();
    if (userId == null) {
      throw new ApiException(ErrorCode.UNAUTHENTICATED);
    }

    // 좋아요를 이미 누른 상태라면,
    if (postLikeRepository.existsByUserIdAndPostId(userId, postId)) {
      throw new ApiException(ErrorCode.LIKE_POST_ERROR);
    }

    // 사용자와 게시글을 조회
    User user = userRepository.findById(userId)
        .orElseThrow(() -> new ApiException(ErrorCode.USER_NOT_FOUND));
    Post post = postRepository.findById(postId)
        .orElseThrow(() -> new ApiException(ErrorCode.POST_NOT_FOUND));

    // PostLike 객체 생성 및 저장
    PostLike postLike = new PostLike(user, post);
    postLikeRepository.save(postLike);

    // 게시글의 좋아요 수 증가
    post.setLikeCount(post.getLikeCount() + 1);
    postRepository.save(post);

    // 해시태그 누적 좋아요 수 변경
    hashtagService.adjustHashtagLikes(postId, true);
  }

  // 좋아요 취소
  @Transactional
  public void unlikePost(Long postId) {
    Long userId = userUtil.getAuthenticatedUserId();
    if (userId == null) {
      throw new ApiException(ErrorCode.UNAUTHENTICATED);
    }

    // 좋아요를 누르지 않은 생태라면,
    if (!postLikeRepository.existsByUserIdAndPostId(userId, postId)) {
      throw new ApiException(ErrorCode.UNLIKE_POST_ERROR);
    }

    // 사용자와 게시글을 조회
    User user = userRepository.findById(userId)
        .orElseThrow(() -> new ApiException(ErrorCode.USER_POST_NOT_FOUND));
    Post post = postRepository.findById(postId)
        .orElseThrow(() -> new ApiException(ErrorCode.POST_NOT_FOUND));

    // PostLike 엔티티 삭제
    postLikeRepository.deleteByUserIdAndPostId(userId, postId);

    // 게시글의 좋아요 수 감소
    post.setLikeCount(post.getLikeCount() - 1);
    postRepository.save(post);

    hashtagService.adjustHashtagLikes(postId, true);
  }

  // 좋아요 수 반환
  public long getPostLikeCount(Long postId) {
    return postLikeRepository.countByPostId(postId);
  }


}
