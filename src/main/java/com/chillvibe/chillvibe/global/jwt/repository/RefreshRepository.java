package com.chillvibe.chillvibe.global.jwt.repository;

import com.chillvibe.chillvibe.global.jwt.entity.Refresh;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RefreshRepository extends JpaRepository<Refresh, Long> {

  Boolean existsByToken(String token);

  @Transactional
  void deleteByToken(String token);
}
