package com.chillvibe.chillvibe.domain.hashtag.dto;

import java.util.List;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class HashtagRequestDto {

  private List<Long> hashtagIds;
}
