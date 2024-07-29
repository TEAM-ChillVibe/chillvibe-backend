package com.chillvibe.chillvibe.domain.playlist.entity;

import com.chillvibe.chillvibe.domain.user.entity.User;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.util.List;

@Entity
@Table(name = "Playlist")
public class Playlist {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(nullable = false)
  private String title;

  @Column(name = "image_url", nullable = false)
  private String imageUrl;

  @ManyToOne
  @JoinColumn(name = "user_id")
  private User user;

  // Playlist가 삭제되면, 연관된 모든 PlaylistTrack 엔티티들도 삭제
  @OneToMany(mappedBy = "playlist", cascade =  CascadeType.ALL, orphanRemoval = true)
  private List<PlaylistTrack> tracks;

}
