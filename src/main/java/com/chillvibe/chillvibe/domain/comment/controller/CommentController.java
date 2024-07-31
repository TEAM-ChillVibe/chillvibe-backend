package com.chillvibe.chillvibe.domain.comment.controller;

import com.chillvibe.chillvibe.domain.comment.dto.CommentRequestDto;
import com.chillvibe.chillvibe.domain.comment.dto.CommentResponseDto;
import com.chillvibe.chillvibe.domain.comment.service.CommentService;
import java.security.Principal;
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
public class CommentController {

  private final CommentService commentService;

  @Autowired
  public CommentController(CommentService commentService) {
    this.commentService = commentService;
  }

  @GetMapping
  public ResponseEntity<List<CommentResponseDto>> getCommentsByPost(@RequestParam Long postId) {
    List<CommentResponseDto> comments = commentService.getCommentsByPost(postId);
    return new ResponseEntity<>(comments, HttpStatus.OK);
  }

  @GetMapping("/user")
  public ResponseEntity<List<CommentResponseDto>> getComments(@RequestParam Long userId) {
    List<CommentResponseDto> comments = commentService.getCommentsByUser(userId);
    return new ResponseEntity<>(comments, HttpStatus.OK);
  }

  @PostMapping
  public ResponseEntity<CommentResponseDto> createComment(@RequestBody CommentRequestDto requestDto,
      Principal principal) {
    String email = principal.getName();
    CommentResponseDto responseDto = commentService.createComment(requestDto, email);
    return new ResponseEntity<>(responseDto, HttpStatus.CREATED);
  }

  // principal 없이 테스트할 때
//  @PostMapping
//  public ResponseEntity<CommentResponseDto> createComment(
//      @RequestBody CommentRequestDto requestDto) {
//    String email = "test@example.com"; // 임시 사용자 정보
//    CommentResponseDto responseDto = commentService.createComment(requestDto, email);
//    return new ResponseEntity<>(responseDto, HttpStatus.CREATED);
//  }

  @PutMapping("/{commentId}")
  public ResponseEntity<CommentResponseDto> updateComment(@PathVariable Long commentId,
      @RequestBody CommentRequestDto requestDto, Principal principal) {
    if (commentId == null) {
      return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    String email = principal.getName();
    CommentResponseDto responseDto = commentService.updateComment(commentId, requestDto, email);

    return new ResponseEntity<>(responseDto, HttpStatus.OK);
  }

  // principal 없이 테스트할 때
//  @PutMapping("/{commentId}")
//  public ResponseEntity<CommentResponseDto> updateComment(@PathVariable Long commentId,
//      @RequestBody CommentRequestDto requestDto) {
//    String email = "test@example.com";
//    CommentResponseDto responseDto = commentService.updateComment(commentId, requestDto, email);
//    return new ResponseEntity<>(responseDto, HttpStatus.OK);
//  }

  @DeleteMapping("/{commentId}")
  public ResponseEntity<CommentResponseDto> deleteComment(@PathVariable Long commentId,
      Principal principal) {
    if (commentId == null) {
      return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    String email = principal.getName();
    commentService.deleteComment(commentId, email);

    return new ResponseEntity<>(HttpStatus.NO_CONTENT);
  }

  // principal 없이 테스트할 때
//  @DeleteMapping("/{commentId}")
//  public ResponseEntity<String> deleteComment(@PathVariable Long commentId) {
//    String email = "test@example.com";
//    commentService.deleteComment(commentId, email);
//    return new ResponseEntity<>(HttpStatus.NO_CONTENT);
//  }
}
