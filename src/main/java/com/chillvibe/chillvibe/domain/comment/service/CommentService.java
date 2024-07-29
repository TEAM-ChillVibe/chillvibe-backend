package com.chillvibe.chillvibe.domain.comment.service;

import com.chillvibe.chillvibe.domain.comment.dto.CommentRequestDto;
import com.chillvibe.chillvibe.domain.comment.dto.CommentResponseDto;
import com.chillvibe.chillvibe.domain.comment.entity.Comment;
import com.chillvibe.chillvibe.domain.comment.repository.CommentRepository;
import com.chillvibe.chillvibe.domain.post.entity.Post;
import com.chillvibe.chillvibe.domain.post.repository.PostRepository;
import com.chillvibe.chillvibe.domain.user.entity.User;
import com.chillvibe.chillvibe.domain.user.repository.UserRepository;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CommentService {

  private final CommentRepository commentRepository;
  private final UserRepository userRepository;
  public final PostRepository postRepository;

  @Autowired
  public CommentService(CommentRepository commentRepository, UserRepository userRepository,
      PostRepository postRepository) {
    this.commentRepository = commentRepository;
    this.userRepository = userRepository;
    this.postRepository = postRepository;
  }

  // 특정 게시글의 모든 댓글 조회 (+ 최신순 정렬)
  @Transactional
  public List<CommentResponseDto> getCommentsByPost(Long postId) {
    Post post = postRepository.findById(postId)
        .orElseThrow(() -> new IllegalArgumentException("Post not found"));

    List<Comment> comments = commentRepository.findByPostOrderByCreatedAtDesc(post);

    return comments.stream()
        .map(CommentResponseDto::new)
        .collect(Collectors.toList());
  }

  // user가 작성한 모든 댓글 조회 (+ 최신순 정렬)
  @Transactional
  public List<CommentResponseDto> getCommentsByUser(Long userId) {
    User user = userRepository.findById(userId)
        .orElseThrow(() -> new IllegalArgumentException("User not found"));

    List<Comment> comments = commentRepository.findByUserOrderByCreatedAtDesc(user);

    return comments.stream()
        .map(CommentResponseDto::new)
        .collect(Collectors.toList());
  }

  // 댓글 생성
  @Transactional
  public CommentResponseDto create(CommentRequestDto requestDto, String email) {
    User user = userRepository.findByEmail(email);

    if (user == null) {
      throw new IllegalArgumentException("User not found");
    }

    Comment comment = new Comment();
    comment.setContent(requestDto.getContent());
    comment.setUser(user);

    Comment savedComment = commentRepository.save(comment);

    return new CommentResponseDto(savedComment);
  }


}
