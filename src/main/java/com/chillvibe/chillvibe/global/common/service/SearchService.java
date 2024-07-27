package com.chillvibe.chillvibe.global.common.service;

import com.chillvibe.chillvibe.domain.post.dto.PostSearchResult;
import com.chillvibe.chillvibe.domain.post.service.PostSearchService;
import com.chillvibe.chillvibe.domain.spotify.dto.SpotifySearchResult;
import com.chillvibe.chillvibe.domain.spotify.service.SpotifyService;
import com.chillvibe.chillvibe.global.common.dto.SearchResultDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class SearchService {

  private final SpotifyService spotifyService;
  private final PostSearchService postService;

  public SearchService(SpotifyService spotifyService, PostSearchService postService) {
    this.spotifyService = spotifyService;
    this.postService = postService;
  }

  public SearchResultDto search(String query, String type, Integer trackOffset, String postCursor,
      int limit) {
    SearchResultDto result = new SearchResultDto();

    if ("all".equalsIgnoreCase(type) || "track".equalsIgnoreCase(type)) {
      SpotifySearchResult trackResult = spotifyService.searchTracks(query, trackOffset, limit);
      result.setTracks(trackResult.getTracks());
      result.setNextTrackOffset(trackResult.getNextOffset());
      result.setHasMoreTracks(trackResult.isHasMore());
    }

    if ("all".equalsIgnoreCase(type) || "post".equalsIgnoreCase(type)) {
      PostSearchResult postResult = postService.searchPosts(query, postCursor, limit);
      result.setPosts(postResult.getPosts());
      result.setNextPostCursor(postResult.getNextCursor());
      result.setHasMorePosts(postResult.isHasMore());
    }

    return result;
  }
}
