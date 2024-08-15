package com.chillvibe.chillvibe.domain.post.dto;

import com.chillvibe.chillvibe.domain.user.dto.UserSimpleResponseDto;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Schema(description = "PostListItemMini 컴포넌트에 필요한 DTO \n\n" +
    "메인 페이지에서 사용됩니다.")
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

  @Schema(description = "게시글 썸네일 이미지")
  private String thumbnailUrl;
}
