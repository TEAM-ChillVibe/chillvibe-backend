package com.chillvibe.chillvibe.domain.post.dto;

import com.chillvibe.chillvibe.domain.user.dto.UserSimpleResponseDto;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class PostSimpleResponseDto {
  @Schema(description = "게시글 Id")
  private Long id;

  @Schema(description = "게시글 제목")
  private String title;

  @Schema(description = "게시글 작성자 정보(유저Id, nickname, 프로필URL")
  private UserSimpleResponseDto user;
  private String thumbnailUrl;

}
