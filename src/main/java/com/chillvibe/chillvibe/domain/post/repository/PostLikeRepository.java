package com.chillvibe.chillvibe.domain.post.repository;

import com.chillvibe.chillvibe.domain.post.entity.PostLike;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PostLikeRepository extends JpaRepository<PostLike, Long> {

  int countByPostId(Long postId);
}
