package com.chillvibe.chillvibe.domain.hashtag.repository;

import com.chillvibe.chillvibe.domain.hashtag.entity.UserHashtag;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserHashtagRepository extends JpaRepository<UserHashtag, Long> {

  List<UserHashtag> findByUserId(Long userId);
}
