package com.chillvibe.chillvibe.domain.spotify.service;

import com.chillvibe.chillvibe.global.error.ErrorCode;
import com.chillvibe.chillvibe.global.error.exception.ApiException;
import jakarta.annotation.PostConstruct;
import java.io.IOException;
import java.time.Instant;
import lombok.extern.slf4j.Slf4j;
import org.apache.hc.core5.http.ParseException;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import se.michaelthelin.spotify.SpotifyApi;
import se.michaelthelin.spotify.exceptions.SpotifyWebApiException;
import se.michaelthelin.spotify.model_objects.credentials.ClientCredentials;

@Service
@Slf4j
public class SpotifyTokenManager {

  private final SpotifyApi spotifyApi;
  private Instant tokenExpiration = Instant.now();

  public SpotifyTokenManager(SpotifyApi spotifyApi) {
    this.spotifyApi = spotifyApi;
  }

  @Scheduled(fixedRate = 3000000) // 토근의 유효기간은 60분으로, 50분마다 재발급
  public synchronized void refreshToken() {
    try {
      if (Instant.now().isAfter(tokenExpiration)) {
        ClientCredentials credentials = spotifyApi.clientCredentials().build().execute();
        spotifyApi.setAccessToken(credentials.getAccessToken());
        tokenExpiration = Instant.now().plusSeconds(credentials.getExpiresIn() - 100);
        log.info("토큰 유효기간 : {}", tokenExpiration);
      }
    } catch (SpotifyWebApiException | IOException | ParseException e) {
      throw new ApiException(ErrorCode.SPOTIFY_TOKEN_REFRESH_ERROR);
    }
  }

  //
  @PostConstruct
  public void initialize() {
      try {
        refreshToken();
        log.info("Spotify Access Token 발급 완료");
      } catch (ApiException e) {
        throw new ApiException(ErrorCode.SPOTIFY_TOKEN_INITIALIZATION_ERROR);
      }
  }
}
