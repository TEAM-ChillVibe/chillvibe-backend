package com.chillvibe.chillvibe.domain.user.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserSimpleResponseDto {

  private Long id;
  private String nickname;
  private String profileUrl;
}
