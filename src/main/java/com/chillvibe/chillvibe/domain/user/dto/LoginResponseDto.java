package com.chillvibe.chillvibe.domain.user.dto;

import com.chillvibe.chillvibe.domain.hashtag.dto.HashtagResponseDto;
import com.chillvibe.chillvibe.domain.user.entity.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class LoginResponseDto {

    private Long userId;
    private String email;
    private String nickname;
    private String introduction;
    private String profileUrl;
    private boolean isPublic;

    public LoginResponseDto(User user) {
        this.userId = user.getId();
        this.email = user.getEmail();
        this.nickname = user.getNickname();
        this.profileUrl = user.getProfileUrl();
        this.introduction = user.getIntroduction();
        this.isPublic = user.isPublic();
    }
}
