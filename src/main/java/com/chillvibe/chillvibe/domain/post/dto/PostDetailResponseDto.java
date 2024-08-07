package com.chillvibe.chillvibe.domain.post.dto;

import com.chillvibe.chillvibe.domain.comment.dto.CommentResponseDto;
import com.chillvibe.chillvibe.domain.hashtag.dto.HashtagResponseDto;
import com.chillvibe.chillvibe.domain.playlist.dto.PlaylistResponseDto;
import com.chillvibe.chillvibe.domain.post.entity.Post;
import com.chillvibe.chillvibe.domain.user.dto.UserInfoResponseDto;
import java.time.LocalDateTime;
import java.util.List;
import lombok.Data;

@Data
public class PostDetailResponseDto {
  private Long id; // 게시글 ID
  private String title; // 게시글 제목
  private String description; // 게시글 설명
  private Integer likeCount; // 게시글 좋아요 갯수
  private LocalDateTime createdAt; // 게시글 생성일
  private LocalDateTime modifiedAt; // 게시글 수정일

  private UserInfoResponseDto user; // 게시글 작성자
  private PlaylistResponseDto playlists; // 플레이리스트
  private List<HashtagResponseDto> hashtags; // 게시글에 달린 해시태그
  private List<CommentResponseDto> comments; // 게시글에 달린 댓글

  public PostDetailResponseDto(Post post, UserInfoResponseDto user, PlaylistResponseDto playlists,
      List<HashtagResponseDto> hashtags, List<CommentResponseDto> comments) {
    this.id = post.getId();
    this.title = post.getTitle();
    this.description = post.getDescription();
    this.likeCount = post.getLikeCount();
    this.createdAt = post.getCreatedAt();
    this.modifiedAt = post.getModifiedAt();
    this.user = user;
    this.playlists = playlists;
    this.hashtags = hashtags;
    this.comments = comments;

  }
}
