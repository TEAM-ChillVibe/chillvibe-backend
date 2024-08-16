package com.chillvibe.chillvibe.domain.hashtag.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import java.util.List;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Schema(description = "특정 사용자의 프로필 또는 특정 게시글에 설정될 해시태그를 변경할때 사용하는 DTO")
@Getter
@Setter
@NoArgsConstructor
public class HashtagRequestDto {

  @Schema(description = "해시태그 ID들")
  private List<Long> hashtagIds;
}
