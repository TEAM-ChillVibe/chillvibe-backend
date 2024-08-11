package com.chillvibe.chillvibe.domain.user.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;
import lombok.Getter;

@Getter
public class UserUpdateRequestDto {

  private String nickname;
  private String introduction;
  private List<Long> hashtagIds;

  @JsonProperty("isPublic")
  private boolean isPublic;
}
