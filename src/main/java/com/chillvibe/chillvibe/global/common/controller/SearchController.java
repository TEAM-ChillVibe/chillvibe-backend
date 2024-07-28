//package com.chillvibe.chillvibe.global.common.controller;
//
//import com.chillvibe.chillvibe.global.common.dto.SearchResultDto;
//import com.chillvibe.chillvibe.global.common.service.SearchService;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.CrossOrigin;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RequestParam;
//import org.springframework.web.bind.annotation.RestController;
//
//@RestController
//@RequestMapping("/api/search")
//@Slf4j
//@CrossOrigin(origins = "http://localhost:3000")
//public class SearchController {
//
//  private final SearchService searchService;
//
//  public SearchController(SearchService searchService) {
//    this.searchService = searchService;
//  }
//
//  @GetMapping
//  public ResponseEntity<SearchResultDto> search(
//      @RequestParam String q,
//      @RequestParam(defaultValue = "all") String type,
//      @RequestParam(required = false) Integer trackOffset,
//      @RequestParam(required = false) String postCursor,
//      @RequestParam(defaultValue = "５") int limit) {
//    SearchResultDto result = searchService.search(q, type, trackOffset, postCursor, limit);
//    return ResponseEntity.ok(result);
//  }
//
//}
