package com.chillvibe.chillvibe.global.mapper;

import com.chillvibe.chillvibe.domain.hashtag.entity.PostHashtag;
import com.chillvibe.chillvibe.domain.post.dto.PostCreateRequestDto;
import com.chillvibe.chillvibe.domain.post.dto.PostListResponseDto;
import com.chillvibe.chillvibe.domain.post.entity.Post;
import com.chillvibe.chillvibe.domain.user.dto.UserSimpleResponseDto;
import com.chillvibe.chillvibe.domain.user.entity.User;
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