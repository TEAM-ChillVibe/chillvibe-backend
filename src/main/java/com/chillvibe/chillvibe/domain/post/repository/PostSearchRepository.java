package com.chillvibe.chillvibe.domain.post.repository;

import com.chillvibe.chillvibe.domain.post.entity.Post;
import java.util.List;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface PostSearchRepository extends JpaRepository<Post, Long> {

  // JPQL 사용
  @Query("SELECT DISTINCT p FROM Post p " +
      "LEFT JOIN FETCH p.user u " +
      "LEFT JOIN FETCH p.playlist pl " +
      "LEFT JOIN FETCH p.postHashtags h " +
      "WHERE (:query IS NULL OR p.title LIKE %:query% OR p.description LIKE %:query%) " +
      "AND (:cursor IS NULL OR p.id < :cursor) " +
      "ORDER BY p.id DESC")
  List<Post> searchPosts(@Param("query") String query,
      @Param("cursor") Long cursor,
      Pageable pageable);
}
