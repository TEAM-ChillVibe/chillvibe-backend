package com.chillvibe.chillvibe.domain.hashtag.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Schema(description = "해시태그 정보들을 반환하는 DTO")
@Getter
@Setter // Mapper 사용 -> Setter 추가 (안하면 태그 다 Null로 나옴)
@NoArgsConstructor
@AllArgsConstructor
public class HashtagResponseDto {

  @Schema(description = "해시태그 ID")
  private Long id;

  @Schema(description = "해시태그 이름")
  private String name;

  @Schema(description = "해시태그 좋아요 수 (해당 해시태그가 달린 게시글들 좋아요 수의 합으로 카운트)")
  private int totalLikes;
}
