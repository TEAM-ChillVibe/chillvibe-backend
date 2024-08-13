package com.chillvibe.chillvibe.domain.post.dto;

import com.chillvibe.chillvibe.domain.post.entity.Post;
import com.chillvibe.chillvibe.domain.user.dto.UserSimpleResponseDto;
import io.swagger.v3.oas.annotations.media.Schema;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class PostListResponseDto {

  @Schema(description = "포스트 Id")
  private Long id;

  @Schema(description = "게시글 제목")
  private String title;

  @Schema(description = "게시글 생성시간")
  private LocalDateTime createdAt;

  @Schema(description = "게시글에 포함된 트랙 갯수")
  private Integer trackCount;

  @Schema(description = "게시글에 포함된 해시태그")
  private Set<String> hashtags;

  @Schema(description = "게시글 작성한 유저정보(유저Id, nickname, 프로필URL")
  private UserSimpleResponseDto user;

  @Schema(description = "게시글에 추가된 like 갯수")
  private Integer likeCount;

  @Schema(description = "게시글에 포함된 트랙의 썸네일 URL")
  private String thumbnailUrl;
}
