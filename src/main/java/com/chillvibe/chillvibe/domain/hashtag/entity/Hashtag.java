package com.chillvibe.chillvibe.domain.hashtag.entity;

import com.chillvibe.chillvibe.domain.hashtag.dto.HashtagDto;
import com.chillvibe.chillvibe.global.common.BaseTimeEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
public class Hashtag extends BaseTimeEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(nullable = false, unique = true)
  private String name;

  @Column(nullable = false)
  private int totalLikes;

  public Hashtag(String name) {
    this.name = name;
    this.totalLikes = 0;
  }

  public HashtagDto toDto() {
    return new HashtagDto(this.id, this.name, this.totalLikes);
  }

  // 누적 좋아요 계산 (+)
  public void increaseTotalLikes(int likeCount) {
    this.totalLikes += likeCount;
  }

  // 누적 좋아요 계산 (-)
  public void decreaseTotalLikes(int likeCount) {
    this.totalLikes = Math.max(0, this.totalLikes - likeCount);
  }
}
