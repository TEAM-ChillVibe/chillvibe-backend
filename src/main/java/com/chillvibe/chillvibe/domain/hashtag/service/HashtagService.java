package com.chillvibe.chillvibe.domain.hashtag.service;

import com.chillvibe.chillvibe.domain.hashtag.dto.HashtagResponseDto;
import java.util.List;


public interface HashtagService {
  // 시스템에 존재하는 모든 해시태그를 조회합니다.
  List<HashtagResponseDto> getAllHashtags();
  // 총 좋아요 수를 기준으로 인기 있는 해시태그를 조회합니다.
  List<HashtagResponseDto> getPopularHashtags(int page, int size) ;
  // 특정 게시글에 속한 해시태그를 조회합니다.
  List<HashtagResponseDto> getHashtagsOfPost(Long postId);
  // 특정 사용자와 관련된 해시태그를 조회합니다.
  List<HashtagResponseDto> getHashtagsOfUser(Long userId);
  // 게시글의 좋아요 수를 기반으로 해당 게시글에 속한 해시태그의 총 좋아요 수를 변화시킵니다.
  void adjustHashtagLikes(Long postId, boolean increase);
  // 특정 사용자의 프로필에 설정될 해시태그를 설정(변경)합니다.
  void updateHashtagsOfUser(List<Long> hashtagIds);
  // 특정 게시글에 설정될 해시태그를 설정(변경)합니다.
  void updateHashtagsOfPost(Long postId, List<Long> hashtagIds);
}
