package com.chillvibe.chillvibe.global.mapper;

import com.chillvibe.chillvibe.domain.comment.dto.CommentResponseDto;
import com.chillvibe.chillvibe.domain.hashtag.dto.HashtagResponseDto;
import com.chillvibe.chillvibe.domain.hashtag.entity.PostHashtag;
import com.chillvibe.chillvibe.domain.playlist.dto.PlaylistResponseDto;
import com.chillvibe.chillvibe.domain.post.dto.PostCreateRequestDto;
import com.chillvibe.chillvibe.domain.post.dto.PostDetailResponseDto;
import com.chillvibe.chillvibe.domain.post.dto.PostListResponseDto;
import com.chillvibe.chillvibe.domain.post.entity.Post;
import com.chillvibe.chillvibe.domain.user.dto.UserInfoResponseDto;
import com.chillvibe.chillvibe.domain.user.dto.UserSimpleResponseDto;
import com.chillvibe.chillvibe.domain.user.entity.User;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface PostMapper {

  @Mapping(target = "id", ignore = true)
  @Mapping(target = "user", ignore = true)
  @Mapping(target = "playlist", ignore = true)
  @Mapping(target = "postHashtag", ignore = true)
  @Mapping(target = "likeCount", constant = "0")
  Post toEntity(PostCreateRequestDto dto);

  @Mapping(target = "trackCount", expression = "java(post.getPlaylist() != null ? post.getPlaylist().getTracks().size() : 0)")
  @Mapping(target = "hashtags", expression = "java(mapHashtags(post.getPostHashtag()))")
  @Mapping(target = "user", expression = "java(mapUser(post.getUser()))")
  @Mapping(target = "likeCount", expression = "java(post.getLikeCount() != null ? post.getLikeCount() : 0)")
  @Mapping(target = "thumbnailUrl", expression = "java(post.getPlaylist() != null ? post.getPlaylist().getThumbnailUrl() : null)")
  PostListResponseDto toPostListDto(Post post);

  @Mapping(target = "id", source = "post.id")
  @Mapping(target = "title", source = "post.title")
  @Mapping(target = "description", source = "post.description")
  @Mapping(target = "likeCount", source = "post.likeCount")
  @Mapping(target = "createdAt", source = "post.createdAt")
  @Mapping(target = "modifiedAt", source = "post.modifiedAt")
  @Mapping(target = "user", source = "userInfoResponseDto")
  @Mapping(target = "playlists", source = "playlistResponseDto")
  @Mapping(target = "hashtags", source = "hashtagResponseDtos")
  @Mapping(target = "comments", source = "commentResponseDtos")
  PostDetailResponseDto toPostDetailDto(Post post,
      UserInfoResponseDto userInfoResponseDto,
      PlaylistResponseDto playlistResponseDto,
      List<HashtagResponseDto> hashtagResponseDtos,
      List<CommentResponseDto> commentResponseDtos);

  default Set<String> mapHashtags(Set<PostHashtag> postHashtags) {
    return postHashtags.stream()
        .map(postHashtag -> postHashtag.getHashtag().getName())
        .collect(Collectors.toSet());
  }

  default UserSimpleResponseDto mapUser(User user) {
    return user != null ? new UserSimpleResponseDto(
        user.getId(),
        user.getNickname(),
        user.getProfileUrl()
    ) : null;
  }


}