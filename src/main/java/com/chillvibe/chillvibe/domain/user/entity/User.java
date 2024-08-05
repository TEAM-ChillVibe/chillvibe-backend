package com.chillvibe.chillvibe.domain.user.entity;

import com.chillvibe.chillvibe.domain.comment.entity.Comment;
import com.chillvibe.chillvibe.domain.hashtag.entity.UserHashtag;
import com.chillvibe.chillvibe.domain.playlist.entity.Playlist;
import com.chillvibe.chillvibe.domain.post.entity.Post;
import com.chillvibe.chillvibe.domain.post.entity.PostLike;
import com.chillvibe.chillvibe.domain.user.dto.UserUpdateRequestDto;
import com.chillvibe.chillvibe.global.common.BaseTimeEntity;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import java.util.ArrayList;
import java.util.List;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Entity
@Getter
@Builder(toBuilder = true)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@SQLDelete(sql = "UPDATE user SET is_delete = true WHERE id=?")
@Where(clause = "is_delete = false")
public class User extends BaseTimeEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(nullable = false, unique = true)
  private String email;

  @Column(nullable = false)
  private String password;

  @Column(nullable = false)
  private String nickname;

  @Column(nullable = false)
  private String profileUrl;

  private String introduction;

  @Column(nullable = false)
  @Builder.Default
  private boolean isPublic = Boolean.TRUE;

  @Column(nullable = false)
  @Builder.Default
  private boolean isDelete = Boolean.FALSE;

  @Builder.Default
  @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
  private List<Post> posts = new ArrayList<>();

  @Builder.Default
  @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
  private List<Comment> comments = new ArrayList<>();

  @Builder.Default
  @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
  private List<PostLike> postLikes = new ArrayList<>();

  @Builder.Default
  @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
  private List<Playlist> playlists = new ArrayList<>();

  @Builder.Default
  @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
  private List<UserHashtag> userGenres = new ArrayList<>();

  public User updateUser(UserUpdateRequestDto userUpdateRequestDto, String imageUrl) {
    this.nickname = userUpdateRequestDto.getNickname();
    this.introduction = userUpdateRequestDto.getIntroduction();
    this.profileUrl = imageUrl;
    this.isPublic = userUpdateRequestDto.isPublic();

    return this;
  }

  public void updatePassword(String newPassword, BCryptPasswordEncoder bCryptPasswordEncoder) {
    this.password = bCryptPasswordEncoder.encode(newPassword);
  }
}
