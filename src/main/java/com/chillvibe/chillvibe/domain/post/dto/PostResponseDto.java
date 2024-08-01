package com.chillvibe.chillvibe.domain.post.dto;

import com.chillvibe.chillvibe.domain.comment.entity.Comment;
import com.chillvibe.chillvibe.domain.hashtag.entity.PostHashtag;
import com.chillvibe.chillvibe.domain.playlist.entity.Playlist;
import com.chillvibe.chillvibe.domain.post.entity.Post;
import com.chillvibe.chillvibe.domain.post.entity.PostLike;
import com.chillvibe.chillvibe.domain.user.entity.User;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter@Setter
@NoArgsConstructor
@Data
public class PostResponseDto {
  private Long id;
  private PostLike postLike;
  private User user;
  private PostHashtag hashtag;
  private Playlist playlist;
  private Comment comment;
  private String title;
  private String description;
  private String postImageUrl;
  private  String postTitleImageUrl;
  private Integer likeCount;


  public PostResponseDto(Post entity){
    this.id = getId();
    this.postLike = getPostLike();
    this.user = getUser();
    this.hashtag = getHashtag();
    this.playlist = getPlaylist();
    this.comment = getComment();
    this.title = getTitle();
    this.description = getDescription();

    this.postImageUrl = getPostImageUrl();
    this.postTitleImageUrl = getPostTitleImageUrl();
    this.likeCount = getLikeCount();
  }

}
