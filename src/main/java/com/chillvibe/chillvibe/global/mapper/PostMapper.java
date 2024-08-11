package com.chillvibe.chillvibe.global.mapper;

import com.chillvibe.chillvibe.domain.post.dto.PostCreateRequestDto;
import com.chillvibe.chillvibe.domain.post.entity.Post;
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


}