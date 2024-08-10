package com.chillvibe.chillvibe.domain.hashtag.entity;

import com.chillvibe.chillvibe.domain.hashtag.dto.HashtagResponseDto;
import com.chillvibe.chillvibe.global.common.BaseTimeEntity;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import java.util.Set;
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

  @OneToMany(mappedBy = "hashtag", cascade = CascadeType.ALL, orphanRemoval = true)
  private Set<PostHashtag> postHashtag;

  public HashtagResponseDto toDto() {
    return new HashtagResponseDto(this.id, this.name, this.totalLikes);
  }

  // 누적 좋아요 계산 (+)
  public void increaseTotalLikes() {
    this.totalLikes += 1;
  }

  // 누적 좋아요 계산 (-)
  public void decreaseTotalLikes() {
    if (this.totalLikes > 0) {
      this.totalLikes -= 1;
    }
  }
}
