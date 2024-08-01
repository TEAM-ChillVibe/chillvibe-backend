package com.chillvibe.chillvibe.domain.spotify.service;

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
    } catch (SpotifyWebApiException e) {
      log.error("Spotify API 에러 발생: {}", e.getMessage());
    } catch (IOException | ParseException e) {
      log.error("토큰 갱신 중 에러 발생: {}", e.getMessage());
    } catch (Exception e) {
      log.error("예기치 않은 에러 발생: {}", e.getClass().getSimpleName(), e);
    }
  }

  //
  @PostConstruct
  public void initialize() {
    refreshToken();
    log.info("Spotify Access Token 발급 완료");
  }
}
