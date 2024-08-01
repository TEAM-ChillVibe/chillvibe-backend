package com.chillvibe.chillvibe.domain.post.repository;

import com.chillvibe.chillvibe.domain.post.entity.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostRepository extends JpaRepository<Post, Long> {

//  //특정 포스트 조회
//  List<Post> findByPostAndIsDeletedFalse(Post post);
//  // 포스트 id로 조회
//  Post findByPostIdAndIsDeletedFalse(Long id);
//  // 특정 user로 포스트 조회
//  Post findByUserIdAndIsDeletedFalse(Long id);
//  // 특정 playlist로 포스트 조회
//  Post findByPlaylistAndIsDeletedFalse(Long id);
  //
//  Page<Post> getByUserId(Long user_Id, Pageable pageable);
  // 인기순 내림차순
  Page<Post> findByIsDeletedFalseOrderByLikeCountDesc(Pageable pageable);
  // 생성일 내림차순
  Page<Post> findByIsDeletedFalseOrderByCreatedAtDesc(Pageable pageable);
//  List<Post> findByIsDeletedFalse();
}