package com.chillvibe.chillvibe.domain.post.repository;

import com.chillvibe.chillvibe.domain.post.entity.Post;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {

  //  //특정 포스트 조회
//  List<Post> findByPostAndDeletedFalse(Post post);
//  // 포스트 id로 조회
//  Post findByPostIdAndDeletedFalse(Long id);

//  // 특정 user로 포스트 조회
//  Page<Post> findByUserIdAndIsDeletedFalse(Long Userid, Pageable pageable);


  //  // 특정 playlist로 포스트 조회
//  Post findByPlaylistAndIsDeletedFalse(Long id);
  //
//  Page<Post> getByUserId(Long user_Id, Pageable pageable);

  // userId를 통해, 게시글을 페이지 형태로 가져온다.
  Page<Post> findByUserId(Long userId, Pageable pageable);

  // 인기순 내림차순
  Page<Post> findByOrderByLikeCountDesc(Pageable pageable);

  // 생성일 내림차순
  Page<Post> findByOrderByCreatedAtDesc(Pageable pageable);
//  List<Post> findByIsDeletedFalse();

  // 게시글 검색, 좋아요 수에 내림차순으로 가져온다.
  Page<Post> findByTitleContainingIgnoreCaseOrderByLikeCountDesc(String title, Pageable pageable);

  // 검색해서 나온 게시글의 갯수
  long countByTitleContainingIgnoreCase(String title);

  // ID 목록에 해당하는 포스트를 페이지네이션하여 조회
  Page<Post> findAllByIdIn(List<Long> ids, Pageable pageable);

  Page<Post> findAllByIdInOrderByLikeCountDesc(List<Long> postIds, Pageable pageable);

  Page<Post> findAllByIdInOrderByCreatedAtDesc(List<Long> postIds, Pageable pageable);
}