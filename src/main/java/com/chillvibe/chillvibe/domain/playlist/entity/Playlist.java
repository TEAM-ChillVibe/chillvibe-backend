package com.chillvibe.chillvibe.domain.playlist.entity;

import com.chillvibe.chillvibe.domain.post.entity.Post;
import com.chillvibe.chillvibe.domain.user.entity.User;
import com.chillvibe.chillvibe.global.common.BaseTimeEntity;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.util.ArrayList;
import java.util.List;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder // Setter 대신 Builder 사용
@Table(name = "Playlist")
public class Playlist extends BaseTimeEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(nullable = false)
  private String title;

  // 처음 빈 플레이리스트를 생성할 때, 기본 이미지를 가져옵니다.
  private String thumbnailUrl;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "user_id")
  private User user;

  // Playlist가 삭제되면, 연관된 모든 PlaylistTrack 엔티티들도 삭제
  @Builder.Default
  @OneToMany(mappedBy = "playlist", cascade =  CascadeType.ALL, orphanRemoval = true)
  private List<PlaylistTrack> tracks = new ArrayList<>();

  // 우선, 플레이리스트가 삭제되면, 해당 플레이리스트가 올라간 모든 글이 삭제된다.
  @Builder.Default
  @OneToMany(mappedBy = "playlist", cascade = CascadeType.ALL, orphanRemoval = true)
  private List<Post> posts = new ArrayList<>();

  public void updateImageUrl(String thumbnailUrl) {
    this.thumbnailUrl = thumbnailUrl;
  }
}