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

  @Scheduled(fixedRate = 600000) // 10분마다 토근 재발급
  public synchronized void refreshToken() {
    try {
        ClientCredentials credentials = spotifyApi.clientCredentials().build().execute();
        spotifyApi.setAccessToken(credentials.getAccessToken());
        log.info("Spotify Access Token 발급 완료 (10분 간격으로 발급됩니다.)");
    } catch (SpotifyWebApiException | IOException | ParseException e) {
      throw new ApiException(ErrorCode.SPOTIFY_TOKEN_REFRESH_ERROR);
    }
  }

  //
  @PostConstruct
  public void initialize() {
      try {
        refreshToken();
      } catch (ApiException e) {
        throw new ApiException(ErrorCode.SPOTIFY_TOKEN_INITIALIZATION_ERROR);
      }
  }
}
