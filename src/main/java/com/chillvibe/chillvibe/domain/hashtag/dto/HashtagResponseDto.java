package com.chillvibe.chillvibe.domain.hashtag.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter // Mapper 사용 -> Setter 추가 (안하면 태그 다 Null로 나옴)
@NoArgsConstructor
@AllArgsConstructor
public class HashtagResponseDto {

  private Long id;
  private String name;
  private int totalLikes;
}
