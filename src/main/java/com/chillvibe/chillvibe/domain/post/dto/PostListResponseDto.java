package com.chillvibe.chillvibe.domain.post.dto;

import com.chillvibe.chillvibe.domain.post.entity.Post;
import com.chillvibe.chillvibe.domain.user.dto.UserSimpleResponseDto;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class PostListResponseDto {
  private Long id;
  private String title;
  private LocalDateTime createdAt;
  private Integer trackCount;
  private Set<String> hashtags;
  private UserSimpleResponseDto user;
  private Integer likeCount;
  private String thumbnailUrl;
}
