package com.chillvibe.chillvibe.domain.playlist.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "PlaylistTrack")
public class PlaylistTrack {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private long id;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "playlist_id", nullable = false)
  private Playlist playlist;

  @Column(nullable = false)
  private String trackId;

  @Column(nullable = false)
  private String name;

  @Column(nullable = false)
  private String artist;

  @Column(nullable = false)
  private String duration;

  // 미리보기는 Null일수도 있습니다.
  // 이와 관련한 처리는 이미 프론트엔드에서 진행했습니다.
  private String previewUrl;

  @Column(nullable = false)
  private String thumbnailUrl;

  public void setPlaylist(Playlist playlist) {
    this.playlist = playlist;
  }

  // 빌더 패턴 구현
  @Builder
  public PlaylistTrack(Playlist playlist,
                        String trackId,
                        String name,
                        String artist,
                        String duration,
                        String previewUrl,
                        String thumbnailUrl
                        ) {
    this.playlist = playlist;
    this.trackId = trackId;
    this.name = name;
    this.artist = artist;
    this.duration = duration;
    this.previewUrl = previewUrl;
    this.thumbnailUrl = thumbnailUrl;
  }

}
