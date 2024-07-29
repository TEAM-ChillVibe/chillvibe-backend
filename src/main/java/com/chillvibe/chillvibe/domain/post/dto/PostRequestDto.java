package com.chillvibe.chillvibe.domain.post.dto;
import com.chillvibe.chillvibe.domain.hashtag.entity.PostHashtag;
import com.chillvibe.chillvibe.domain.playlist.entity.Playlist;
import lombok.Data;
import lombok.Getter;


@Getter
@Data
public class PostRequestDto {

  private String title;
  private PostHashtag hashtag;
  private String discription;
  private String postImageURL;
  private  String postTitleImageURL;
  private Playlist playlist;

}