package com.chillvibe.chillvibe.domain.comment.controller;

import com.chillvibe.chillvibe.domain.comment.dto.CommentRequestDto;
import com.chillvibe.chillvibe.domain.comment.dto.CommentResponseDto;
import com.chillvibe.chillvibe.domain.comment.service.CommentService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/comments")
@Tag(name = "Comment", description = "댓글 API")
public class CommentController {

  private final CommentService commentService;

  @Autowired
  public CommentController(CommentService commentService) {
    this.commentService = commentService;
  }

  @GetMapping("/byPost")
  @Operation(summary = "게시글의 댓글들 가져오기", description = "게시글의 댓글들을 가져오는데 사용하는 API")
  public ResponseEntity<List<CommentResponseDto>> getCommentsByPost(@RequestParam Long postId) {
    List<CommentResponseDto> comments = commentService.getCommentsByPost(postId);
    return new ResponseEntity<>(comments, HttpStatus.OK);
  }

  @GetMapping("/byUser")
  @Operation(summary = "나의 댓글들 가져오기", description = "마이 페이지 - Comments를 조회하는데 사용하는 API")
  public ResponseEntity<List<CommentResponseDto>> getComments() {
    List<CommentResponseDto> comments = commentService.getCommentsByUser();
    return new ResponseEntity<>(comments, HttpStatus.OK);
  }

  @PostMapping
  @Operation(summary = "댓글 생성하기", description = "유저가 댓글을 작성하는데 사용하는 API")
  public ResponseEntity<CommentResponseDto> createComment(
      @RequestBody @Valid CommentRequestDto requestDto) {
    CommentResponseDto responseDto = commentService.createComment(requestDto);
    return new ResponseEntity<>(responseDto, HttpStatus.CREATED);
  }

  @PutMapping("/{commentId}")
  @Operation(summary = "댓글 수정하기", description = "댓글을 작성한 유저가 댓글을 수정하는데 사용하는 API")
  public ResponseEntity<CommentResponseDto> updateComment(@PathVariable Long commentId,
      @RequestBody CommentRequestDto requestDto) {
    if (commentId == null) {
      return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    CommentResponseDto responseDto = commentService.updateComment(commentId, requestDto);
    return new ResponseEntity<>(responseDto, HttpStatus.OK);
  }

  @DeleteMapping("/{commentId}")
  @Operation(summary = "댓글 삭제하기", description = "댓글을 작성한 유저가 댓글을 삭제하는데 사용하는 API")
  public ResponseEntity<String> deleteComment(@PathVariable Long commentId) {
    commentService.deleteComment(commentId);
    return new ResponseEntity<>("Comment deleted successfully.", HttpStatus.OK);
  }

}
