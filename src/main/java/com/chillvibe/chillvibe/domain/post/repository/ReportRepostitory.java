package com.chillvibe.chillvibe.domain.post.repository;

import com.chillvibe.chillvibe.domain.post.entity.Report;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReportRepostitory extends JpaRepository<Report, Long> {

  boolean existsByUserIdAndPostId(Long userId, Long postId);

}
