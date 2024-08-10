package com.chillvibe.chillvibe.domain.user.repository;

import com.chillvibe.chillvibe.domain.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface UserRepository extends JpaRepository<User, Long> {

  Boolean existsByEmail(String email);

  User findByEmail(String email);

  @Modifying
  @Query("UPDATE User u SET u.isDelete = false WHERE u.id = :id")
  void restore(Long id);
}
