package com.chillvibe.chillvibe.domain.post.service;

import com.chillvibe.chillvibe.domain.hashtag.entity.PostHashtag;
import com.chillvibe.chillvibe.domain.hashtag.repository.HashtagRepository;
import com.chillvibe.chillvibe.domain.hashtag.repository.PostHashtagRepository;
import com.chillvibe.chillvibe.domain.hashtag.service.HashtagService;
import com.chillvibe.chillvibe.domain.playlist.entity.Playlist;
import com.chillvibe.chillvibe.domain.playlist.repository.PlaylistRepository;
import com.chillvibe.chillvibe.domain.post.dto.PostCreateRequestDto;
import com.chillvibe.chillvibe.domain.post.dto.PostDetailResponseDto;
import com.chillvibe.chillvibe.domain.post.dto.PostListResponseDto;
import com.chillvibe.chillvibe.domain.post.dto.PostResponseDto;
import com.chillvibe.chillvibe.domain.post.dto.PostUpdateRequestDto;
import com.chillvibe.chillvibe.domain.post.entity.Post;
import com.chillvibe.chillvibe.domain.post.repository.PostRepository;
import com.chillvibe.chillvibe.global.error.ErrorCode;
import com.chillvibe.chillvibe.global.error.exception.ApiException;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


public interface PostService {

  // 게시글 생성
  PostListResponseDto createPost(PostCreateRequestDto requestDto);

  // 전체 게시글 조회 - 최신순 & 인기순
  Page<PostListResponseDto> getPosts(String sortBy, int page, int size);

  // 특정 게시글 상세 조회
  PostDetailResponseDto getPostById(Long postId);

  // 게시글 수정
  Long updatePost(Long id, PostUpdateRequestDto postUpdateRequestDto);

  //게시글 삭제
  void deletePost(Long postId);

  Page<PostResponseDto> getPostsByUserId(Long userId, Pageable pageable);
  Post savePost(Post post);
//  PostResponseDto updatePost(Long postId, String title, String description, String postTitleImageUrl, Long playlistId, List<Long> hashtagIds);
  Page<PostListResponseDto> getPostsByHashtagId(Long hashtagId, Pageable pageable);


}