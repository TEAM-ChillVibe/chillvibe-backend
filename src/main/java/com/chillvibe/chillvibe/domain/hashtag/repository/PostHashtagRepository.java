package com.chillvibe.chillvibe.domain.hashtag.repository;

import com.chillvibe.chillvibe.domain.hashtag.entity.PostHashtag;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PostHashtagRepository extends JpaRepository<PostHashtag, Long> {

  List<PostHashtag> findByPostId(Long postId);

  void deleteByPostId(Long postId);
}
