package com.chillvibe.chillvibe.domain.post.service;

import com.chillvibe.chillvibe.domain.hashtag.entity.PostHashtag;
import com.chillvibe.chillvibe.domain.hashtag.repository.HashtagRepository;
import com.chillvibe.chillvibe.domain.hashtag.repository.PostHashtagRepository;
import com.chillvibe.chillvibe.domain.hashtag.service.HashtagService;
import com.chillvibe.chillvibe.domain.playlist.entity.Playlist;
import com.chillvibe.chillvibe.domain.playlist.repository.PlaylistRepository;
import com.chillvibe.chillvibe.domain.post.dto.PostCreateRequestDto;
import com.chillvibe.chillvibe.domain.post.dto.PostListResponseDto;
import com.chillvibe.chillvibe.domain.post.dto.PostResponseDto;
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

  // 게시글 ID로 검색하기
  Post getPostById(Long id);

//  Page<Post> getAllPosts(String soltBy, Pageable pageable);
//  Page<Post> getLikePosts(String soltBy, Pageable pageable);
  Page<PostResponseDto> getPostsByUserId(Long userId, Pageable pageable);
  Post savePost(Post post);
  PostResponseDto updatePost(Long postId, String title, String description, String postTitleImageUrl, Long playlistId, List<Long> hashtagIds);
//  PostListResponseDto createPost(String title, String description, String postTitleImageUrl,
//      Long playlistId, List<Long> hashtagIds);


  Page<PostListResponseDto> getPostsByHashtagId(Long hashtagId, Pageable pageable);
  void deletePost(Long postId);

}