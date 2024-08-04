package com.chillvibe.chillvibe.domain.comment.controller;

import com.chillvibe.chillvibe.domain.comment.dto.CommentRequestDto;
import com.chillvibe.chillvibe.domain.comment.dto.CommentResponseDto;
import com.chillvibe.chillvibe.domain.comment.service.CommentService;
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

  @GetMapping("/byPost")
  public ResponseEntity<List<CommentResponseDto>> getCommentsByPost(@RequestParam Long postId) {
    List<CommentResponseDto> comments = commentService.getCommentsByPost(postId);
    return new ResponseEntity<>(comments, HttpStatus.OK);
  }

  @GetMapping("/byUser")
  public ResponseEntity<List<CommentResponseDto>> getComments(@RequestParam Long userId) {
    List<CommentResponseDto> comments = commentService.getCommentsByUser(userId);
    return new ResponseEntity<>(comments, HttpStatus.OK);
  }

  @PostMapping
  public ResponseEntity<CommentResponseDto> createComment(
      @RequestBody CommentRequestDto requestDto) {
    CommentResponseDto responseDto = commentService.createComment(requestDto);
    return new ResponseEntity<>(responseDto, HttpStatus.CREATED);
  }

  @PutMapping("/{commentId}")
  public ResponseEntity<CommentResponseDto> updateComment(@PathVariable Long commentId,
      @RequestBody CommentRequestDto requestDto) {
    if (commentId == null) {
      return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    CommentResponseDto responseDto = commentService.updateComment(commentId, requestDto);
    return new ResponseEntity<>(responseDto, HttpStatus.OK);
  }

  @DeleteMapping("/{commentId}")
  public ResponseEntity<String> deleteComment(@PathVariable Long commentId) {
    commentService.deleteComment(commentId);
    return new ResponseEntity<>("Comment deleted successfully.", HttpStatus.OK);
  }

}
