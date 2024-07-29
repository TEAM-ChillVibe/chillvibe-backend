package com.chillvibe.chillvibe.domain.post.dto;

import com.chillvibe.chillvibe.domain.hashtag.entity.PostHashTag;
import com.chillvibe.chillvibe.domain.playlist.entity.PlayList;
import lombok.Data;
import lombok.Getter;


@Getter
@Data
public class PostRequestDto {

  private String title;
  private PostHashTag hashtag;
  private String discription;
  private String postImageURL;
  private  String postTitleImageURL;
  private PlayList playList;

}