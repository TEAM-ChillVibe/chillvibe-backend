package com.chillvibe.chillvibe.domain.post.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import java.util.List;
import lombok.Data;

// 수정 가능한 항목
// -> 제목, 설명, 게시글 해시태그들.
@Data
public class PostUpdateRequestDto {

  @Schema(description = "게시글 제목")
  @Size(min = 1, max = 50, message = "게시글 제목은 1자 이상, 50자 이상 입력이 가능합니다.")
  @NotBlank(message = "게시글 제목을 입력하세요.")
  private String title;

  @Schema(description = "게시글 소개 글")
  @Size(min = 1, max = 10000, message = "게시글 설명은 1자 이상 10000자 이하로 입력이 가능합니다.")
  @NotBlank(message = "게시글 내용을 입력하세요.")
  private String description;

  @Schema(description = "선택한 해시태그들 ID")
  @Size(max = 5, message = "게시글의 해시태그는 최대 5개까지 선택할 수 있습니다.")
  private List<Long> hashtagIds;
}
