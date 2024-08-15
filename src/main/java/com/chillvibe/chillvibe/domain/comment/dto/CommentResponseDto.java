package com.chillvibe.chillvibe.domain.comment.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import java.time.LocalDateTime;
import lombok.Data;
import lombok.NoArgsConstructor;

@Schema(description = "댓글 정보를 받아오는 DTO")
@Data
@NoArgsConstructor
public class CommentResponseDto {

  @Schema(description = "댓글 ID")
  private Long id;

  @Schema(description = "댓글 내용")
  private String content;

  @Schema(description = "댓글 생성일")
  private LocalDateTime createdAt;

  @Schema(description = "댓글 수정일")
  private LocalDateTime modifiedAt;

  @Schema(description = "댓글 작성자 ID")
  private Long userId;

  @Schema(description = "댓글 작성한 게시글 ID")
  private Long postId;

  @Schema(description = "댓글 작성한 유저 닉네임")
  private String userNickname;

  @Schema(description = "댓글 작성한 유저 프로필 이미지")
  private String userProfileUrl;

  @Schema(description = "댓글 작성한 유저 이메일")
  private String userEmail;

  @Schema(description = "댓글 작성한 게시글 이름")
  private String postTitle;

  @Schema(description = "댓글 작성한 게시글의 저자 이름")
  private String postAuthor;

  @Schema(description = "게시글 작성자 유저 프로필 이미지")
  private String postAuthorProfileUrl;

  @Schema(description = "게시글의 썸네일 이미지 (플레이리스트 이미지와 동일)")
  private String postTitleImageUrl;
}
