package com.chillvibe.chillvibe.global.jwt.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import java.util.Date;
import lombok.Getter;

@Entity
@Getter
public class Refresh {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private String email;
  private String token;
  private Date expiration;

  public static Refresh createRefreshEntity(String email, String token, Long expiration) {

    Date expiredMs = new Date(System.currentTimeMillis() + expiration);

    Refresh refresh = new Refresh();
    refresh.email = email;
    refresh.token = token;
    refresh.expiration = expiredMs;

    return refresh;
  }

}
