package com.chillvibe.chillvibe.global.common.dto;

import com.chillvibe.chillvibe.domain.post.dto.PostListResponseDto;
import com.chillvibe.chillvibe.domain.spotify.dto.TrackSearchDto;
import com.fasterxml.jackson.annotation.JsonInclude;
import java.util.List;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Setter
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class SearchResponseDto {
  private List<PostListResponseDto> postContent; // 게시글 검색 결과
  private List<TrackSearchDto> trackContent; // 트랙 검색 결과
  private long totalPosts; // 검색 조건에 맞는 총 게시글 수
  private long totalTracks; // 검색 조건에 맞는 총 트랙의 수 (최대 50)
  private String type; // 검색 타입 (all, post, track)
  private int currentPage; // 현재 페이지 번호
  private int pageSize; // 한 페이지에 포함된 아이템 수
  private boolean isLast; // 현재 페이지가 마지막 페이지인지 확인

  // 생성자 -> Type = all일때 (default)
  public SearchResponseDto(List<PostListResponseDto> posts, List<TrackSearchDto> tracks, long totalPosts, long totalTracks) {
    this.postContent = posts;
    this.trackContent = tracks;
    this.totalPosts = totalPosts;
    this.totalTracks = totalTracks;
    this.type = "all";
    this.currentPage = 0;
    this.pageSize = posts.size() + tracks.size();
    this.isLast = true;
  }

  // 생성자 -> 게시글 검색 결과
  public static SearchResponseDto ofPosts(List<PostListResponseDto> posts, long totalPosts, int currentPage, int pageSize, boolean isLast) {
    SearchResponseDto dto = new SearchResponseDto();
    dto.postContent = posts;
    dto.trackContent = null;
    dto.totalPosts = totalPosts;
    dto.totalTracks = 0;
    dto.type = "post";
    dto.currentPage = currentPage;
    dto.pageSize = pageSize;
    dto.isLast = isLast;
    return dto;
  }

  // 생성자 -> 트랙 검색 결과
  public static SearchResponseDto ofTracks(List<TrackSearchDto> tracks, long totalTracks, int currentPage, int pageSize, boolean isLast) {
    SearchResponseDto dto = new SearchResponseDto();
    dto.trackContent = tracks;
    dto.postContent = null;
    dto.totalTracks = totalTracks;
    dto.totalPosts = 0;
    dto.type = "track";
    dto.currentPage = currentPage;
    dto.pageSize = pageSize;
    dto.isLast = isLast;
    return dto;
  }
}
