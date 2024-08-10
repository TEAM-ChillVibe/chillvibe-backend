package com.chillvibe.chillvibe.domain.post.dto;

import jakarta.validation.constraints.Size;
import java.util.List;
import lombok.Data;

// 수정 가능한 항목
// -> 제목, 설명, 게시글 해시태그들.
@Data
public class PostUpdateRequestDto {
  private String title;
  private String description;

  @Size(max = 5, message = "게시글의 해시태그는 최대 5개까지 선택할 수 있습니다.")
  private List<Long> hashtagIds; // 선택한 해시태그들 ID
}
