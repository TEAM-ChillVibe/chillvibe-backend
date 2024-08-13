package com.chillvibe.chillvibe.domain.post.dto;

import com.chillvibe.chillvibe.domain.comment.dto.CommentResponseDto;
import com.chillvibe.chillvibe.domain.hashtag.dto.HashtagResponseDto;
import com.chillvibe.chillvibe.domain.playlist.dto.PlaylistResponseDto;
import com.chillvibe.chillvibe.domain.post.entity.Post;
import com.chillvibe.chillvibe.domain.user.dto.UserInfoResponseDto;
import io.swagger.v3.oas.annotations.media.Schema;
import java.time.LocalDateTime;
import java.util.List;
import lombok.Data;

@Data
public class PostDetailResponseDto {
  @Schema(description = "게시글 ID")
  private Long id; // 게시글 ID

  @Schema(description = "게시글 제목")
  private String title; // 게시글 제목

  @Schema(description = "게시글 설명")
  private String description; // 게시글 설명

  @Schema(description = "게시글 좋아요 갯수")
  private Integer likeCount; // 게시글 좋아요 갯수

  @Schema(description = "게시글 생성일")
  private LocalDateTime createdAt; // 게시글 생성일

  @Schema(description = "게시글 수정일")
  private LocalDateTime modifiedAt; // 게시글 수정일


  @Schema(description = "게시글 작성자")
  private UserInfoResponseDto user; // 게시글 작성자

  @Schema(description = "플레이리스트(ID, 제목, 썸네일URL, 수정시간, 트랙갯수, 트랙리스트")
  private PlaylistResponseDto playlists; // 플레이리스트

  @Schema(description = "게시글에 달린 해시태그(Id, 이름, like 갯수")
  private List<HashtagResponseDto> hashtags; // 게시글에 달린 해시태그

  @Schema(description = "게시글에 달린 댓글")
  private List<CommentResponseDto> comments; // 게시글에 달린 댓글

}
