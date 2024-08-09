package com.chillvibe.chillvibe.domain.user.dto;

import java.util.List;
import lombok.Getter;

@Getter
public class UserUpdateRequestDto {

  private String nickname;
  private String introduction;
  private List<Long> hashtagIds;
  private boolean isPublic;
}
