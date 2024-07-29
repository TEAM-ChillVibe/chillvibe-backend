package com.chillvibe.chillvibe.domain.comment.controller;

import com.chillvibe.chillvibe.domain.comment.dto.CommentRequestDto;
import com.chillvibe.chillvibe.domain.comment.dto.CommentResponseDto;
import com.chillvibe.chillvibe.domain.comment.service.CommentService;
import java.security.Principal;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
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
  public ResponseEntity<List<CommentResponseDto>> getComments(@RequestParam Long postId,
      @RequestParam Long userId) {
    List<CommentResponseDto> comments;

    if (postId != null) {
      comments = commentService.getCommentsByPost(postId);
    } else if (userId != null) {
      comments = commentService.getCommentsByUser(userId);
    } else {
      return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }
    return new ResponseEntity<>(comments, HttpStatus.OK);
  }

  @PostMapping
  public ResponseEntity<CommentResponseDto> createComment(
      @RequestBody CommentRequestDto requestDto, Principal principal) {
    if (principal == null) {
      return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
    }
    String email = principal.getName();
    CommentResponseDto responseDto = commentService.create(requestDto, email);
    return new ResponseEntity<>(responseDto, HttpStatus.CREATED);
  }


}
