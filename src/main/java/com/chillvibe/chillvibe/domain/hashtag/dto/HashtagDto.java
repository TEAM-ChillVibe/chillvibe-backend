package com.chillvibe.chillvibe.domain.hashtag.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class HashtagDto {

  private Long id;
  private String name;
  private int totalLikes;
}
