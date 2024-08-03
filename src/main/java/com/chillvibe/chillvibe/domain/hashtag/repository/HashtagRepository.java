package com.chillvibe.chillvibe.domain.hashtag.repository;

import com.chillvibe.chillvibe.domain.hashtag.entity.Hashtag;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface HashtagRepository extends JpaRepository<Hashtag, Long> {

  // totalLikes를 기준으로 태그를 인기순 정렬, totalLikes가 같은 데이터의 경우 랜덤 정렬
  @Query("SELECT h FROM Hashtag h ORDER BY h.totalLikes DESC, RAND()")
  Page<Hashtag> findTopByOrderByTotalLikesDescRandom(Pageable pageable);

  List<Hashtag> findByIdIn(List<Long> ids);
}