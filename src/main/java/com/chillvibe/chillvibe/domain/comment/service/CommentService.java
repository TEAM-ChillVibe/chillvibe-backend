package com.chillvibe.chillvibe.domain.comment.service;

import com.chillvibe.chillvibe.domain.comment.dto.CommentRequestDto;
import com.chillvibe.chillvibe.domain.comment.dto.CommentResponseDto;
import java.util.List;

public interface CommentService {
  // 특정 게시글의 모든 댓글 조회 (+ 최신순 정렬)
  List<CommentResponseDto> getCommentsByPost(Long postId) ;
  // user가 작성한 모든 댓글 조회 (+ 최신순 정렬)
  List<CommentResponseDto> getCommentsByUser() ;
  // 댓글 생성
  CommentResponseDto createComment(CommentRequestDto requestDto);
  // 댓글 수정
  CommentResponseDto updateComment(Long commentId, CommentRequestDto requestDto) ;
  // 댓글 삭제
  void deleteComment(Long commentId);
}
