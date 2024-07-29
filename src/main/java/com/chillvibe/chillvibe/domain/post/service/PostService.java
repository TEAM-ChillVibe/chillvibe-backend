package com.chillvibe.chillvibe.domain.post.service;

import com.chillvibe.chillvibe.domain.comment.repository.CommentRepository;
import com.chillvibe.chillvibe.domain.hashtag.entity.Hashtag;
import com.chillvibe.chillvibe.domain.hashtag.repository.HashtagRepository;
import com.chillvibe.chillvibe.domain.playlist.repository.PlaylistRepository;
import com.chillvibe.chillvibe.domain.post.dto.PostResponseDto;
import com.chillvibe.chillvibe.domain.post.repository.PostRepository;
import com.chillvibe.chillvibe.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class PostService {

  private final PostRepository postRepository;
  private final UserRepository userRepository;
  private final HashtagRepository hashtagRepository;
  private final CommentRepository commentRepository;
  private final PlaylistRepository playlistRepository;

  // Hashtag로 게시글 조회
//  public Page<PostResponseDto> getAllPostByHashtag(Long Hashtag_Id, Pageable pageable){
////    Hashtag hashTag = PostRepository.findByHashtagId(hashtagid. pageable);
//  }

}
