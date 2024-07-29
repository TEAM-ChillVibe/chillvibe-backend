package com.chillvibe.chillvibe.domain.post.service;
import com.chillvibe.chillvibe.domain.comment.repository.CommentRepository;
import com.chillvibe.chillvibe.domain.hashtag.entity.HashTag;
import com.chillvibe.chillvibe.domain.hashtag.repository.HashTagRepository;
import com.chillvibe.chillvibe.domain.playlist.repository.PlayListRepository;
import com.chillvibe.chillvibe.domain.post.dto.PostResponseDTO;
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
  private final HashTagRepository hashTagRepository;
  private final CommentRepository commentRepository;
  private final PlayListRepository playListRepository;

  // Hashtag로 게시글 조회
  public Page<PostResponseDTO> getAllPostByHashtag(Long Hashtag_Id, Pageable pageable){
    HashTag hashTag = PostRepository.findbyHashtagId(hashtag_id. pageable);
  }

}
