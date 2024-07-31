package com.chillvibe.chillvibe.domain.hashtag.repository;

import com.chillvibe.chillvibe.domain.hashtag.entity.Hashtag;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface HashtagRepository extends JpaRepository<Hashtag, Long> {

  @Query("SELECT h FROM Hashtag h ORDER BY h.totalLikes DESC")
  Page<Hashtag> findTopByOrderByTotalLikesDESC(Pageable pageable);
}