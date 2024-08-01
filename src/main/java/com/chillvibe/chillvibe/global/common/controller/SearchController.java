package com.chillvibe.chillvibe.global.common.controller;

import com.chillvibe.chillvibe.domain.post.service.PostService;
import com.chillvibe.chillvibe.domain.spotify.service.SpotifyService;
import com.chillvibe.chillvibe.global.common.dto.SearchResponseDto;
import com.chillvibe.chillvibe.global.common.service.SearchService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/search")
@Slf4j
public class SearchController {

  private final SearchService searchService;

  public SearchController(SearchService searchService) {
    this.searchService = searchService;
  }

  // 전역에 나오는 검색 바.
  // 검색어(q)를 입력하여 검색이 가능하다.
  // all = 트랙과 게시글 5개씩
  // track, post = 트랙 게시글 각각 50개를 보여준다.
  @GetMapping
  public ResponseEntity<SearchResponseDto> search(
      @RequestParam String q,
      @RequestParam(defaultValue = "all") String type,
      @RequestParam(defaultValue = "0") int page,
      @RequestParam(defaultValue = "20") int size) {
    SearchResponseDto response = searchService.search(q, type, page, size);
    return ResponseEntity.ok(response);
  }

}
