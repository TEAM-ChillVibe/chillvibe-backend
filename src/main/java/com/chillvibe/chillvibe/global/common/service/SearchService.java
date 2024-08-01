package com.chillvibe.chillvibe.global.common.service;

import com.chillvibe.chillvibe.domain.post.dto.PostSearchDto;
import com.chillvibe.chillvibe.domain.post.entity.Post;
import com.chillvibe.chillvibe.domain.post.repository.PostRepository;
import com.chillvibe.chillvibe.domain.spotify.dto.SpotifySearchResult;
import com.chillvibe.chillvibe.domain.spotify.dto.TrackSearchDto;
import com.chillvibe.chillvibe.domain.spotify.service.SpotifyService;
import com.chillvibe.chillvibe.global.common.dto.SearchResponseDto;
import java.util.List;
import java.util.stream.Collectors;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class SearchService {

  // 게시글의 정보는 DB에서,
  // 트랙의 정보는 Spotify API를 이용하여 받아온다.
  private final SpotifyService spotifyService;
  private final PostRepository postRepository;

  public SearchService(SpotifyService spotifyService, PostRepository postRepository) {
    this.spotifyService = spotifyService;
    this.postRepository = postRepository;
  }

  public SearchResponseDto search(String query, String type, int page, int size) {
    if ("all".equals(type)) {
      return searchAll(query);
    } else if ("post".equals(type)) {
      return searchPosts(query, page, size);
    } else if ("track".equals(type)) {
      return searchTracks(query, page, size);
    }
    throw new IllegalArgumentException("Invalid search type");
  }

  // 통합 검색 postRepository
  // type = all일 경우, 검색어에 대한 트랙 5개,
  private SearchResponseDto searchAll(String query) {
    List<PostSearchDto> posts = postRepository.findByTitleContainingIgnoreCase(query,
            PageRequest.of(0, 5))
        .stream().map(this::convertToPostSearchDto).collect(Collectors.toList());
    List<TrackSearchDto> tracks = spotifyService.searchTracks(query, 0, 5).getTracks();
    long totalPosts = postRepository.countByTitleContainingIgnoreCase(query);
    long totalTracks = Math.min(spotifyService.searchTracks(query, 0, 1).getTotalTracks(), 50);

    return new SearchResponseDto(posts, tracks, totalPosts, totalTracks);
  }

  // 게시글 검색
  private SearchResponseDto searchPosts(String query, int page, int size) {
    Page<Post> postPage = postRepository.findByTitleContainingIgnoreCase(query,
        PageRequest.of(page, size));
    List<PostSearchDto> posts = postPage.getContent().stream().map(this::convertToPostSearchDto)
        .collect(Collectors.toList());
    return SearchResponseDto.ofPosts(posts, postPage.getTotalElements(), page, size,
        postPage.isLast());
  }

  // 트랙 검색
  private SearchResponseDto searchTracks(String query, int page, int size) {
    SpotifySearchResult result = spotifyService.searchTracks(query, page * size, size);
    return SearchResponseDto.ofTracks(result.getTracks(), Math.min(result.getTotalTracks(), 50),
        page, size, !result.isHasMore());
  }


  // Post -> PostDto 변환
  private PostSearchDto convertToPostSearchDto(Post post) {
    return new PostSearchDto(
        post.getId(),
        post.getTitle(),
        post.getCreatedAt(),
        getTrackCountForPost(post),
        getPostHashtagsForPost(post),
        post.getUser().getNickname(),
        post.getUser().getProfileUrl(),
        post.getPostLike().size()
    );
  }

  // 게시글의 좋아요 수 추출
  private int getTrackCountForPost(Post post) {
    if (post.getPlaylist() != null) {
      return post.getPlaylist().getTracks().size();
    }
    return 0;
  }

  // 게시글에 달린 태그 추출
  private List<String> getPostHashtagsForPost(Post post) {
    return post.getPostHashtag().stream()
        .map(postHashtag -> postHashtag.getHashtag().getName())
        .collect(Collectors.toList());
  }

}
