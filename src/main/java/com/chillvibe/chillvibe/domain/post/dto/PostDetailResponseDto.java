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

@Schema(description = "게시글 상세 페이지 조회시 사용하는 DTO")
@Data
public class PostDetailResponseDto {

  @Schema(description = "게시글 ID")
  private Long id;

  @Schema(description = "게시글 제목")
  private String title;

  @Schema(description = "게시글 소개 글")
  private String description;

  @Schema(description = "게시글 좋아요 갯수")
  private Integer likeCount;

  @Schema(description = "게시글 생성일")
  private LocalDateTime createdAt;

  @Schema(description = "게시글 수정일")
  private LocalDateTime modifiedAt;

  @Schema(description = "로그인한 유저가 좋아요 눌렀는지 여부")
  private boolean userLike;

  @Schema(description = "게시글 작성자")
  private UserInfoResponseDto user;

  @Schema(description = "플레이리스트(ID, 제목, 썸네일URL, 수정시간, 트랙갯수, 트랙리스트")
  private PlaylistResponseDto playlists;

  @Schema(description = "게시글에 달린 해시태그(Id, 이름, like 갯수")
  private List<HashtagResponseDto> hashtags;

  @Schema(description = "게시글에 달린 댓글")
  private List<CommentResponseDto> comments;

  private boolean isDeleted;
}
