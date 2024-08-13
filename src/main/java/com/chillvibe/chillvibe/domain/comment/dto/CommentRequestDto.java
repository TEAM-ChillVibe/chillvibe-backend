package com.chillvibe.chillvibe.domain.comment.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class CommentRequestDto {

  private Long userId;
  private Long postId;

  @NotBlank(message = "댓글을 입력해주세요.")
  @Size(min = 1, max = 255, message = "댓글은 1자 이상 255자 이하여야 합니다.")
  private String content;
}
