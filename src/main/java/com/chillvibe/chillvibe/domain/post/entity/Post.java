package com.chillvibe.chillvibe.domain.post.entity;

import com.chillvibe.chillvibe.domain.comment.entity.Comment;
import com.chillvibe.chillvibe.domain.hashtag.entity.PostHashtag;
import com.chillvibe.chillvibe.domain.playlist.entity.Playlist;
import com.chillvibe.chillvibe.domain.playlist.entity.PlaylistTrack;
import com.chillvibe.chillvibe.domain.user.entity.User;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Lob;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import java.util.ArrayList;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.DialectOverride.Wheres;
import org.hibernate.annotations.SQLDelete;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "post")
@Entity
//@SQLDelete(sql = "UPDATE post SET is_deleted = true WHERE post_id = ?")
//@Wheres(clause = "is_deleted = false")
public class Post {


  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "post_id")
  private Long id;

  @OneToMany(mappedBy = "post", cascade =  CascadeType.ALL, orphanRemoval = true)
  private List<PostLike> likes;

  @ManyToOne
  @JoinColumn(name = "user_id")
  private User user;

  @ManyToOne
  @JoinColumn(name = "postHashtag_id")
  private PostHashtag hashtag;

  @ManyToOne
  @JoinColumn(name = "playList_id")
  private Playlist playList;

  @OneToMany(mappedBy = "post", cascade = CascadeType.ALL, orphanRemoval = true)
  private List<Comment> comment = new ArrayList<>();

  @Column(length = 1000, nullable = false)
  private String title;

  @Lob
  private String discription;

  @Column(length = 1000)
  private String postImageURL;

  @Column(length = 1000, nullable = false)
  private  String postTitleImageURL;

  @ColumnDefault("0")
  @Column(name = "likeCount", nullable = false)
  private Integer likeCount;
}
