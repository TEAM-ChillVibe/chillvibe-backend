//package com.chillvibe.chillvibe.domain.spotify.service;
//
//import jakarta.annotation.PostConstruct;
//import java.time.Instant;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.scheduling.annotation.Scheduled;
//import org.springframework.stereotype.Service;
//import se.michaelthelin.spotify.SpotifyApi;
//import se.michaelthelin.spotify.model_objects.credentials.ClientCredentials;
//
//@Service
//@Slf4j
//public class SpotifyTokenManager {
//
//  private final SpotifyApi spotifyApi;
//  private Instant tokenExpiration = Instant.now();
//
//  public SpotifyTokenManager(SpotifyApi spotifyApi) {
//    this.spotifyApi = spotifyApi;
//  }
//
//  @Scheduled(fixedRate = 3000000) // 토근의 유효기간은 60분으로, 50분마다 재발급
//  public void refreshToken() {
//    try {
//      if (Instant.now().isAfter(tokenExpiration)) {
//        ClientCredentials credentials = spotifyApi.clientCredentials().build().execute();
//        spotifyApi.setAccessToken(credentials.getAccessToken());
//        tokenExpiration = Instant.now().plusSeconds(credentials.getExpiresIn() - 100);
//        log.info("토큰 유효기간 : {}", tokenExpiration);
//      }
//    } catch (Exception e) {
//      log.error("토큰 재발급 실패", e);
//    }
//  }
//
//  //
//  @PostConstruct
//  public void initialize() {
//    log.info("프로젝트 시작  토큰 발급");
//    refreshToken();
//    log.info("Spotify Access Token 값 : {}", spotifyApi.getAccessToken()); // 토큰 값 확인.
//  }
//}
