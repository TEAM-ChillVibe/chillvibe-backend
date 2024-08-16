package com.chillvibe.chillvibe.global.mapper;

import com.chillvibe.chillvibe.domain.hashtag.dto.HashtagResponseDto;
import com.chillvibe.chillvibe.domain.user.dto.UserInfoResponseDto;
import com.chillvibe.chillvibe.domain.user.entity.User;
import java.util.List;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface UserMapper {

  @Mapping(target = "userId", source = "user.id")
  @Mapping(target = "email", source = "user.email")
  @Mapping(target = "nickname", source = "user.nickname")
  @Mapping(target = "profileUrl", source = "user.profileUrl")
  @Mapping(target = "introduction", source = "user.introduction")
  @Mapping(target = "public", expression = "java(user.isPublic())")
  UserInfoResponseDto userToUserInfoResponseDto(User user, List<HashtagResponseDto> hashtags);
}
