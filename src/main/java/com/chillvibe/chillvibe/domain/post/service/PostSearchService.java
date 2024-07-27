package com.chillvibe.chillvibe.domain.post.service;

import com.chillvibe.chillvibe.domain.post.dto.PostSearchDto;
import com.chillvibe.chillvibe.domain.post.dto.PostSearchResult;
import com.chillvibe.chillvibe.domain.post.entity.Post;
import com.chillvibe.chillvibe.domain.post.repository.PostSearchRepository;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
public class PostSearchService {

  private final PostSearchRepository postRepository;

  public PostSearchService(PostSearchRepository postRepository) {
    this.postRepository = postRepository;
  }

  public PostSearchResult searchPosts(String query, String cursor, int limit) {
    Long cursorId = cursor != null ? Long.parseLong(cursor) : null;
    Pageable pageable = PageRequest.of(0, limit + 1, Sort.by(Sort.Direction.DESC, "id"));

    List<Post> posts = postRepository.searchPosts(query, cursorId, pageable);

    boolean hasMore = posts.size() > limit;
    List<PostSearchDto> postDtos = posts.stream()
        .limit(limit)
        .map(this::convertToDto)
        .collect(Collectors.toList());

    String nextCursor = hasMore ? String.valueOf(posts.get(limit - 1).getId()) : null;

    return new PostSearchResult(postDtos, nextCursor, hasMore);
  }

  private PostSearchDto convertToDto(Post post) {
    return new PostSearchDto(
        post.getId(),
        post.getTitle(),
        post.getCreatedAt(),
        post.getPlaylist().getTracks().size(),
        post.getPostHashtags().stream()
            .map(postHashtag -> postHashtag.getHashtag().getName())
            .limit(5)
            .collect(Collectors.toList()),
        post.getUser().getNickname(),
        post.getUser().getProfileImageUrl(),
        post.getLikes().size()
    );
  }

  private String generateCursor(Post post) {
    // 커서 생성 로직 (예: post.getId().toString())
    return post.getId().toString();
  }

}
