package com.chillvibe.chillvibe.domain.playlist.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "PlaylistTrack")
public class PlaylistTrack {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private long id;

  @ManyToOne
  @JoinColumn(name = "playlist_id", nullable = false)
  private Playlist playlist;

  @Column(name = "track_id", nullable = false)
  private String trackId;

  @Column(name = "name", nullable = false)
  private String name;

  @Column(name = "artist", nullable = false)
  private String artist;

  @Column(name = "duration", nullable = false)
  private String duration;

  @Column(name = "preview_url")
  private String previewUrl;

  @Column(name = "thumbnail_url", nullable = false)
  private String thumbnailUrl;

}
