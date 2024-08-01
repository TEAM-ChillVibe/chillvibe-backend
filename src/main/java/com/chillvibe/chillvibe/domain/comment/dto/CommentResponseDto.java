package com.chillvibe.chillvibe.domain.comment.dto;

import com.chillvibe.chillvibe.domain.comment.entity.Comment;
import java.time.LocalDateTime;
import lombok.Data;

@Data
public class CommentResponseDto {

  private Long id;
  private String content;
  private LocalDateTime createdAt;
  private LocalDateTime modifiedAt;
  private Long userId;
  private Long postId;

  public CommentResponseDto(Comment comment) {
    this.id = comment.getId();
    this.content = comment.getContent();
    this.createdAt = comment.getCreatedAt();
    this.modifiedAt = comment.getModifiedAt();
    this.userId = comment.getUser().getId();
    this.postId = comment.getPost().getId();
  }

}
