package com.chillvibe.chillvibe.global.mapper;

import com.chillvibe.chillvibe.domain.comment.dto.CommentRequestDto;
import com.chillvibe.chillvibe.domain.comment.dto.CommentResponseDto;
import com.chillvibe.chillvibe.domain.comment.entity.Comment;
import com.chillvibe.chillvibe.domain.post.entity.Post;
import com.chillvibe.chillvibe.domain.user.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

/**
 * CommentMapper 인터페이스는 Comment 엔티티와 DTO 간의 변환을 담당.
 * MapStruct를 사용하여 자동으로 매핑 구현체를 생성.
 */
@Mapper(componentModel = "spring")
public interface CommentMapper {

  /**
   * CommentRequestDto를 Comment 엔티티로 변환합니다.
   * @param dto 변환할 CommentRequestDto
   * @return 변환된 Comment 엔티티
   */
  @Mapping(target = "id", ignore = true)
  @Mapping(target = "user", source = "userId")
  @Mapping(target = "post", source = "postId")
  Comment toEntity(CommentRequestDto dto);


  /**
   * Comment 엔티티를 CommentResponseDto로 변환합니다.
   * @param comment 변환할 Comment 엔티티
   * @return 변환된 CommentResponseDto
   */
  @Mapping(target = "userId", source = "user.id")
  @Mapping(target = "postId", source = "post.id")
  @Mapping(target = "userNickname", source = "user.nickname")
  @Mapping(target = "userProfileUrl", source = "user.profileUrl")
  @Mapping(target = "userEmail", source = "user.email")
  @Mapping(target = "postTitle", source = "post.title")
  @Mapping(target = "postAuthor", source = "post.user.nickname")
  @Mapping(target = "postAuthorProfileUrl", source = "post.user.profileUrl")
  @Mapping(target = "postTitleImageUrl", source = "post.playlist.thumbnailUrl")
  CommentResponseDto toDto(Comment comment);

  default User mapToUser(Long userId) {
    if (userId == null) {
      return null;
    }
    return User.builder()
        .id(userId)
        .build();
  }

  default Post mapToPost(Long postId) {
    if (postId == null) {
      return null;
    }
    Post post = new Post();
    post.setId(postId);
    return post;
  }

}
