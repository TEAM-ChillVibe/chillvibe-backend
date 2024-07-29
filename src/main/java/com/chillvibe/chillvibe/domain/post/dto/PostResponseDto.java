package com.chillvibe.chillvibe.domain.post.dto;

import com.chillvibe.chillvibe.domain.comment.entity.Comment;
import com.chillvibe.chillvibe.domain.hashtag.entity.PostHashTag;
import com.chillvibe.chillvibe.domain.playlist.entity.PlayList;
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
  private PostHashTag hashtag;
  private PlayList playList;
  private Comment comment;
  private String title;
  private String discription;
  private String postImageURL;
  private  String postTitleImageURL;
  private Integer likeCount;


  public PostResponseDTO(Post entity){
    this.id = getId();
    this.postLike = getPostLike();
    this.user = getUser();
    this.hashtag = getHashtag();
    this.playList = getPlayList();
    this.comment = getComment();
    this.title = getTitle();
    this.discription = getDiscription();
    this.postImageURL = getPostImageURL();
    this.postTitleImageURL = getPostTitleImageURL();
    this.likeCount = getLikeCount();
  }

}
