package com.chillvibe.chillvibe.domain.post.dto;


import java.time.LocalDateTime;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;

// 검색 결과에 보여야 하는 항목은
// 게시글 제목, 생성일, 게시글에 존재하는 플레이리스트의 트랙 수,
// 게시글에 달린 장르 태그 5개만, 게시글 작성자의 닉네임,
// 게시글 작성자의 프로필 사진, 해당 게시글의 좋아요 수
@Getter
@AllArgsConstructor
public class PostSearchDto {

  private Long id; // 게시글 아이디
  private String title; // 게시글 제목
  private LocalDateTime createdAt; // 게시글 생성일
  private int trackCount; // 게시글에 존재하는 플레이리스트의 트랙 수
  private List<String> postHashtags; // 게시글에 달린 태그
  private String author; // 게시글 저자
  private String authorProfileImageUrl; // 게시글 작성자의 프로필 사진
  private int likeCount; // 해당 게시글 좋아요 갯수
}
