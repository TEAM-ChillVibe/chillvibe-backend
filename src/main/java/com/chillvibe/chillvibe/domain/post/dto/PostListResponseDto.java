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

@Schema(description = "PostListItem 컴포넌트에 필요한 DTO \n\n" +
    "게시글 조회, 마이페이지 - 내 게시글 등의 곳에서 사용됩니다.")
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
