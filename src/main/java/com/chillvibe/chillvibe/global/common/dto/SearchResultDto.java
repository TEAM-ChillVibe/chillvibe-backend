//package com.chillvibe.chillvibe.global.common.dto;
//
//import com.chillvibe.chillvibe.domain.post.dto.PostSearchDto;
//import com.chillvibe.chillvibe.domain.spotify.dto.TrackDto;
//import com.fasterxml.jackson.annotation.JsonInclude;
//import java.util.List;
//import lombok.Data;
//import lombok.NoArgsConstructor;
//import lombok.Setter;
//
//@NoArgsConstructor
//@Setter
//@Data
//@JsonInclude(JsonInclude.Include.NON_NULL)
//public class SearchResultDto {
//
//  // 검색 결과의 Track, Post를 반환
//  private List<TrackDto> tracks;
//  private List<PostSearchDto> posts;
//
//  // 무한 스크롤
//  // 다음 페이지 요청할 때 사용
//  private int nextTrackOffset;
//  private String nextPostCursor;
//
//  // 추가 데이터의 존재 여부를 표시
//  // 없다면 + more이 나타나지 않게 하는 방법도 고려.
//  private boolean hasMoreTracks;
//  private boolean hasMorePosts;
//
//}
