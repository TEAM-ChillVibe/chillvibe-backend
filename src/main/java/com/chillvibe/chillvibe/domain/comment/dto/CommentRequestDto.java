package com.chillvibe.chillvibe.domain.comment.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Schema(description = "댓글 생성 요청을 보내는 DTO")
@Data
public class CommentRequestDto {

  @Schema(description = "댓글을 작성한 유저 id")
  private Long userId;

  @Schema(description = "댓글 작성한 게시글의 id")
  private Long postId;

  @Schema(description = "댓글 내용")
  @NotBlank(message = "댓글을 입력해주세요.")
  @Size(min = 1, max = 255, message = "댓글은 1자 이상 255자 이하여야 합니다.")
  private String content;
}
