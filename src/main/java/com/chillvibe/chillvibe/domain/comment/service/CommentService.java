package com.chillvibe.chillvibe.domain.comment.service;

import com.chillvibe.chillvibe.domain.comment.dto.CommentRequestDto;
import com.chillvibe.chillvibe.domain.comment.dto.CommentResponseDto;
import com.chillvibe.chillvibe.domain.comment.entity.Comment;
import com.chillvibe.chillvibe.domain.comment.repository.CommentRepository;
import com.chillvibe.chillvibe.domain.post.entity.Post;
import com.chillvibe.chillvibe.domain.post.repository.PostRepository;
import com.chillvibe.chillvibe.domain.user.entity.User;
import com.chillvibe.chillvibe.domain.user.repository.UserRepository;
import com.chillvibe.chillvibe.global.error.ErrorCode;
import com.chillvibe.chillvibe.global.error.exception.ApiException;
import com.chillvibe.chillvibe.global.jwt.util.UserUtil;
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
  public final UserUtil userUtil;

  @Autowired
  public CommentService(CommentRepository commentRepository, UserRepository userRepository,
      PostRepository postRepository, UserUtil userUtil) {
    this.commentRepository = commentRepository;
    this.userRepository = userRepository;
    this.postRepository = postRepository;
    this.userUtil = userUtil;
  }

  // 특정 게시글의 모든 댓글 조회 (+ 최신순 정렬)
  @Transactional
  public List<CommentResponseDto> getCommentsByPost(Long postId) {
    Post post = postRepository.findById(postId)
        .orElseThrow(() -> new ApiException(ErrorCode.POST_COMMENT_NOT_FOUND));

    List<Comment> comments = commentRepository.findByPostOrderByCreatedAtDesc(post);

    return comments.stream()
        .map(CommentResponseDto::new)
        .collect(Collectors.toList());
  }

  // user가 작성한 모든 댓글 조회 (+ 최신순 정렬)
  @Transactional
  public List<CommentResponseDto> getCommentsByUser() {
    Long userId = userUtil.getAuthenticatedUserId();

    User user = userRepository.findById(userId)
        .orElseThrow(() -> new ApiException(ErrorCode.USER_COMMENT_NOT_FOUND));

    List<Comment> comments = commentRepository.findByUserOrderByCreatedAtDesc(user);

    return comments.stream()
        .map(comment -> {
          CommentResponseDto dto = new CommentResponseDto(comment);
          dto.setPostAuthor(comment.getUser().getNickname());
          dto.setPostAuthorProfileUrl(comment.getUser().getProfileUrl());
          return dto;
        })
        .collect(Collectors.toList());
  }

  // 댓글 생성
  @Transactional
  public CommentResponseDto createComment(CommentRequestDto requestDto) {
    Long userId = userUtil.getAuthenticatedUserId();

    if (userId == null) {
      throw new ApiException(ErrorCode.UNAUTHENTICATED);
    }

    User user = userRepository.findById(userId)
        .orElseThrow(() -> new ApiException(ErrorCode.USER_NOT_FOUND));
    Post post = postRepository.findById(requestDto.getPostId())
        .orElseThrow(() -> new ApiException(ErrorCode.POST_COMMENT_NOT_FOUND));

    Comment comment = new Comment();
    comment.setContent(requestDto.getContent());
    comment.setUser(user);
    comment.setPost(post);

    Comment savedComment = commentRepository.save(comment);

    return new CommentResponseDto(savedComment);
  }

  // 댓글 수정
  @Transactional
  public CommentResponseDto updateComment(Long commentId, CommentRequestDto requestDto) {
    Long userId = userUtil.getAuthenticatedUserId();

    if (userId == null) {
      throw new ApiException(ErrorCode.UNAUTHENTICATED);
    }

    User user = userRepository.findById(userId)
        .orElseThrow(() -> new ApiException(ErrorCode.USER_NOT_FOUND));
    Comment comment = commentRepository.findById(commentId)
        .orElseThrow(() -> new ApiException(ErrorCode.COMMENT_NOT_FOUND));

    if (!comment.getUser().getId().equals(user.getId())) {
      throw new ApiException(ErrorCode.UNAUTHORIZED_ACCESS);
    }

    comment.setContent(requestDto.getContent());

    Comment updatedComment = commentRepository.save(comment);
    return new CommentResponseDto(updatedComment);
  }

  // 댓글 삭제
  @Transactional
  public void deleteComment(Long commentId) {
    Long userId = userUtil.getAuthenticatedUserId();

    if (userId == null) {
      throw new ApiException(ErrorCode.UNAUTHENTICATED);
    }

    User user = userRepository.findById(userId)
        .orElseThrow(() -> new ApiException(ErrorCode.USER_NOT_FOUND));
    Comment comment = commentRepository.findById(commentId)
        .orElseThrow(() -> new ApiException(ErrorCode.COMMENT_NOT_FOUND));

    if (comment.getUser().getId() != user.getId()) {
      throw new ApiException(ErrorCode.UNAUTHORIZED_ACCESS);
    }

    commentRepository.delete(comment);
  }

}
