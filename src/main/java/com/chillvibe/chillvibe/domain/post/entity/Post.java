package com.chillvibe.chillvibe.domain.post.entity;

import com.chillvibe.chillvibe.domain.comment.entity.Comment;
import com.chillvibe.chillvibe.domain.hashtag.entity.PostHashtag;
import com.chillvibe.chillvibe.domain.playlist.entity.Playlist;
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
import jakarta.persistence.Lob;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "post")
@Entity
public class Post extends BaseTimeEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @OneToMany(mappedBy = "post", cascade = CascadeType.ALL, orphanRemoval = true)
  private List<PostLike> postLike = new ArrayList<>();

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "user_id")
  private User user;

  @OneToMany(mappedBy = "post", cascade = CascadeType.ALL, orphanRemoval = true)
  private Set<PostHashtag> postHashtag;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "playlist_id")
  private Playlist playlist;

  @OneToMany(mappedBy = "post", cascade = CascadeType.ALL, orphanRemoval = true)
  private List<Comment> comments = new ArrayList<>();

  @Column(length = 1000, nullable = false)
  private String title;

  @Lob
  private String description;

  @Column(length = 1000, nullable = true)
  private String postTitleImageUrl;

  @ColumnDefault("0")
  @Column(name = "likeCount", nullable = true)
  private Integer likeCount;
}
