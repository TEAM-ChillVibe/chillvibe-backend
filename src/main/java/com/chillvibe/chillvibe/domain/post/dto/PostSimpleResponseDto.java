package com.chillvibe.chillvibe.domain.post.dto;

import com.chillvibe.chillvibe.domain.user.dto.UserSimpleResponseDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class PostSimpleResponseDto {
  private Long id;
  private String title;
  private UserSimpleResponseDto user;
  private String thumbnailUrl;

}
