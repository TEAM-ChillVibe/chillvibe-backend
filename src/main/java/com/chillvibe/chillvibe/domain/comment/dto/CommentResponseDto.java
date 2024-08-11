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
}
