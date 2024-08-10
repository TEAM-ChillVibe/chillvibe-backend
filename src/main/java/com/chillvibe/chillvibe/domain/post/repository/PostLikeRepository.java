package com.chillvibe.chillvibe.domain.post.repository;

import com.chillvibe.chillvibe.domain.post.entity.PostLike;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface PostLikeRepository extends JpaRepository<PostLike, Long> {

  //사용자가 좋아요 눌렀는지 확인
  boolean existsByUserIdAndPostId(Long userId, Long PostId);

  // userId와 postId로 좋아요 취소
  void deleteByUserIdAndPostId(Long userId, Long postId);

  // 좋아요 카운트
  int countByPostId(Long postId);

  // 사용자 ID로 좋아요한 게시물 ID 목록 조회
  @Query("SELECT pl.post.id FROM PostLike pl WHERE pl.user.id = :userId")
  List<Long> findPostIdsByUserId(Long userId);
}
