package com.chillvibe.chillvibe.domain.post.service;

import com.chillvibe.chillvibe.domain.post.dto.PostCreateRequestDto;
import com.chillvibe.chillvibe.domain.post.dto.PostDetailResponseDto;
import com.chillvibe.chillvibe.domain.post.dto.PostListResponseDto;
import com.chillvibe.chillvibe.domain.post.dto.PostUpdateRequestDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;


public interface PostService {

  // 게시글 생성
  Long createPost(PostCreateRequestDto requestDto);

  // 전체 게시글 조회 - 최신순 & 인기순
  Page<PostListResponseDto> getPosts(String sortBy, int page, int size);

  // 특정 게시글 상세 조회
  PostDetailResponseDto getPostById(Long postId);

  // 로그인한 유저 자신의 게시글 보기
  Page<PostListResponseDto> getUserPosts(String sortBy, Pageable pageable);

  // 유저 페이지의 게시글 보기 (isPublic 확인 필요)
  Page<PostListResponseDto> getPostsByUserId(Long userId, String sortby, Pageable pageable);

  // 게시글 수정
  Long updatePost(Long id, PostUpdateRequestDto postUpdateRequestDto);

  // 게시글 삭제
  void deletePost(Long postId);

  // 해시태그별로 게시글 가져오기
  Page<PostListResponseDto> getPostsByHashtagId(String sortBy, Long hashtagId, Pageable pageable);

  // 게시글 검색 (좋아요 순으로 내림차순)
  Page<PostListResponseDto> getPostSearchResults(String query, Pageable pageable);

  // 좋아요한 게시글 조회
  Page<PostListResponseDto> getPostsByUserLiked(Pageable pageable);

  // 메인 페이지 - 전체 게시글 좋아요 순으로 6개씩 페이지네이션
  Page<PostListResponseDto> getMainPostsByLikes(int page, int size);
}