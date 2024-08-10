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

  public PostListResponseDto(Post post) {
    this.id = post.getId();
    this.title = post.getTitle();
    this.createdAt = post.getCreatedAt();
    this.trackCount = post.getPlaylist() != null ? post.getPlaylist().getTracks().size() : 0;
    this.hashtags = post.getPostHashtag().stream()
        .map(postHashtag -> postHashtag.getHashtag().getName())
        .collect(Collectors.toSet());
//        .toList();
    this.user = post.getUser() != null ? new UserSimpleResponseDto(
        post.getUser().getId(),
        post.getUser().getNickname(),
        post.getUser().getProfileUrl()
    ) : null;
    this.likeCount = post.getLikeCount() != null ? post.getLikeCount() : 0;
    this.thumbnailUrl = post.getPlaylist().getThumbnailUrl();
  }
}
