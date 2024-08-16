package com.chillvibe.chillvibe.global.mapper;

import com.chillvibe.chillvibe.domain.hashtag.dto.HashtagResponseDto;
import com.chillvibe.chillvibe.domain.hashtag.entity.Hashtag;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface HashtagMapper {

  HashtagResponseDto hashtagToHashtagResponseDto(Hashtag hashtag);

  List<HashtagResponseDto> hashtagsToHashtagResponseDtos(List<Hashtag> hashtags);
}
