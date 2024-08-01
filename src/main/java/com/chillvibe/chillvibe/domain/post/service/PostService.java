package com.chillvibe.chillvibe.domain.post.service;

import com.chillvibe.chillvibe.domain.comment.repository.CommentRepository;
import com.chillvibe.chillvibe.domain.hashtag.entity.Hashtag;
import com.chillvibe.chillvibe.domain.hashtag.repository.HashtagRepository;
import com.chillvibe.chillvibe.domain.playlist.entity.Playlist;
import com.chillvibe.chillvibe.domain.playlist.repository.PlaylistRepository;
import com.chillvibe.chillvibe.domain.post.dto.PostResponseDto;
import com.chillvibe.chillvibe.domain.post.entity.Post;
import com.chillvibe.chillvibe.domain.post.repository.PostLikeRepository;
import com.chillvibe.chillvibe.domain.post.repository.PostRepository;
import com.chillvibe.chillvibe.domain.user.repository.UserRepository;
import com.chillvibe.chillvibe.global.error.ErrorCode;
import com.chillvibe.chillvibe.global.error.exception.ApiException;
import com.chillvibe.chillvibe.global.error.ErrorCode;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class PostService {

  private final PostRepository postRepository;
  private final PlaylistRepository playlistRepository;
  private final HashtagRepository hashtagRepository;

  //생성일 순
  public Page<Post> getAllPosts(String soltBy, Pageable pageable){
    return postRepository.findByPostAnsIsDeletedFalseOrderCreatedAtDesc(pageable);
  }
  //인기글 순
  public Page<Post> getLikePosts(String soltBy, Pageable pageable) {
    if ("like".equalsIgnoreCase(soltBy)) {
      return postRepository.findByPostAndIsDeletedFalseOrderLikeCountDesc(pageable);
    } else {
      return postRepository.findByPostAnsIsDeletedFalseOrderCreatedAtDesc(pageable);
    }
  }
  //포스트 ID로 조회
  public Post getPostById(Long id){
    return postRepository.findById(id).orElseThrow(() -> new ApiException(ErrorCode.POST_NOT_FOUND));
  }


  //새포스트 저장
  public Post savePost(Post post){
    return postRepository.save(post);
  }

  public Post createPost(String title, String description, String postTitleImageUrl, List<String> postImageUrl, Long playlistId) {
    Playlist playlist = playlistRepository.findById(playlistId)
        .orElseThrow(() -> new ApiException(ErrorCode.POST_NOT_FOUND));

//    List<Hashtag> hashtags = new ArrayList<>();
//    for (String name : hashtagNames) {
//      Hashtag hashtag = hashtagRepository.findByName(name).orElseGet(() -> new Hashtag(name));
//      hashtags.add(hashtag);
//    }

    Post post = new Post();
    post.setTitle(title);
    post.setDescription(description);
    post.setPostTitleImageUrl(postTitleImageUrl);
    post.setPostImageUrl(postImageUrl);
    post.setPlaylist(playlist);

    return postRepository.save(post);
  }
}