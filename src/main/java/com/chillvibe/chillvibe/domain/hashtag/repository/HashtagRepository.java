package com.chillvibe.chillvibe.domain.hashtag.repository;

import com.chillvibe.chillvibe.domain.hashtag.entity.Hashtag;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface HashtagRepository extends JpaRepository<Hashtag, Long> {

  @Query(value = "SELECT * FROM hashtag ORDER BY total_likes DESC LIMIT :limit", nativeQuery = true)
  List<Hashtag> findTopNByOrderByTotalLikesDesc(@Param("limit") int limit);
}