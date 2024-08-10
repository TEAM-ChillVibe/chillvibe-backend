package com.chillvibe.chillvibe.domain.comment.dto;

import com.chillvibe.chillvibe.domain.comment.entity.Comment;
import java.time.LocalDateTime;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class CommentResponseDto {

  private Long id;
  private String content;
  private LocalDateTime createdAt;
  private LocalDateTime modifiedAt;
  private Long userId;
  private Long postId;
  private String userNickname;
  private String userProfileUrl;
  private String userEmail;
  private String postTitle;
  private String postAuthor;
  private String postAuthorProfileUrl;
  private String postTitleImageUrl;

  public CommentResponseDto(Comment comment) {
    this.id = comment.getId();
    this.content = comment.getContent();
    this.createdAt = comment.getCreatedAt();
    this.modifiedAt = comment.getModifiedAt();
    this.userId = comment.getUser().getId();
    this.postId = comment.getPost().getId();
    this.userNickname = comment.getUser().getNickname();
    this.userProfileUrl = comment.getUser().getProfileUrl();
    this.userEmail = comment.getUser().getEmail();
    this.postTitle = comment.getPost().getTitle();
    this.postAuthor = comment.getPost().getUser().getNickname();
    this.postAuthorProfileUrl = comment.getPost().getUser().getProfileUrl();
    this.postTitleImageUrl = comment.getPost().getPlaylist().getThumbnailUrl();
  }
}
