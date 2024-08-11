package com.chillvibe.chillvibe.domain.post.service;

import com.chillvibe.chillvibe.domain.post.dto.PostCreateRequestDto;
import com.chillvibe.chillvibe.domain.post.dto.PostDetailResponseDto;
import com.chillvibe.chillvibe.domain.post.dto.PostListResponseDto;
import com.chillvibe.chillvibe.domain.post.dto.PostSimpleResponseDto;
import com.chillvibe.chillvibe.domain.post.dto.PostUpdateRequestDto;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;


public interface PostService {

  // 게시글 생성
  Long createPost(PostCreateRequestDto requestDto);

  // 전체 게시글 조회 - 최신순 & 인기순
  Page<PostListResponseDto> getPosts(String sortBy, int page, int size);

  // 특정 게시글 상세 조회
  PostDetailResponseDto getPostById(Long postId);

  // 유저 페이지의 게시글 보기 (isPublic 확인 필요)
  Page<PostListResponseDto> getPostsByUserId(Long userId, Pageable pageable);

  // 게시글 수정
  Long updatePost(Long id, PostUpdateRequestDto postUpdateRequestDto);
  // PostResponseDto updatePost(Long postId, String title, String description, String
  // postTitleImageUrl, Long playlistId, List<Long> hashtagIds);

  // 게시글 삭제
  void deletePost(Long postId);

  // 해시태그별로 게시글 가져오기
  Page<PostListResponseDto> getPostsByHashtagId(String sortBy, Long hashtagId, Pageable pageable);

  // 게시글 검색 (좋아요 순으로 내림차순)
  Page<PostListResponseDto> getPostSearchResults(String query, Pageable pageable);


  // 좋아요한 게시글 조회
  Page<PostListResponseDto> getPostsByUserLiked(Pageable pageable);
  // 전체 게시글중 좋아요순으로 상위6개의 게시글 가져오기
  List<PostSimpleResponseDto> getMainPostsByLikes();
}