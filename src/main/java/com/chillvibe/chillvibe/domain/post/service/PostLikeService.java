package com.chillvibe.chillvibe.domain.post.service;

import java.util.List;

public interface PostLikeService {

  // 좋아요 기능은 로그인한 사용자만 가능하다.
  // 게시글에 좋아요 추가
  void likePost(Long postId);

  // 게시글에 좋아요 취소
  void unlikePost(Long postId);

  List<Long> getLikedPostIdsByUser();

}
