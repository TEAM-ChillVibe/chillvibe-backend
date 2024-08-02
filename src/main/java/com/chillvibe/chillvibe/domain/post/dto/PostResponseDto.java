package com.chillvibe.chillvibe.domain.post.dto;

import com.chillvibe.chillvibe.domain.comment.entity.Comment;
import com.chillvibe.chillvibe.domain.hashtag.entity.PostHashtag;
import com.chillvibe.chillvibe.domain.playlist.entity.Playlist;
import com.chillvibe.chillvibe.domain.post.entity.Post;
import com.chillvibe.chillvibe.domain.post.entity.PostLike;
import com.chillvibe.chillvibe.domain.user.entity.User;
import java.util.List;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter@Setter
@NoArgsConstructor
@Data
public class PostResponseDto {

  private Long id;
  private List<PostLike> postLike;
  private User user;
  private PostHashtag hashtag;
  private Playlist playlist;
  private Comment comment;
  private String title;
  private String description;
  private String postTitleImageUrl;
  private Integer likeCount;
  private String message;


  public PostResponseDto(Post post) {
    this.id = post.getId();
    this.postLike = post.getPostLike();
    this.user = post.getUser();
    this.playlist = post.getPlaylist();
    this.title = post.getTitle();
    this.description = post.getDescription();
    this.postTitleImageUrl = post.getPostTitleImageUrl();
    this.likeCount = post.getLikeCount();
  }
}


