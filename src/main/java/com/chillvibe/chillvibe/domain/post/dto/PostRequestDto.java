package com.chillvibe.chillvibe.domain.post.dto;
import com.chillvibe.chillvibe.domain.hashtag.entity.PostHashtag;
import com.chillvibe.chillvibe.domain.playlist.entity.Playlist;
import lombok.Data;
import lombok.Getter;
import org.springframework.web.multipart.MultipartFile;


@Getter
@Data
public class PostRequestDto {

  private String title;
  private PostHashtag hashtag;
  private String description;
  private String postImageUrl;
  private  String postTitleImageUrl;
  private Playlist playlist;
  private MultipartFile mainTitleImage;
  private MultipartFile[] contentImages;

}