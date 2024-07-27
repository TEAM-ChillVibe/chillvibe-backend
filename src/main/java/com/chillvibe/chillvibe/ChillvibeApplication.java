package com.chillvibe.chillvibe;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.scheduling.annotation.EnableScheduling;

// (exclude = {SecurityAutoConfiguration.class})
// 이 부분 설정하지 않으면 api 요청이 안됩니다!
@SpringBootApplication(exclude = {SecurityAutoConfiguration.class})
@EnableScheduling
public class ChillvibeApplication {

  public static void main(String[] args) {
    SpringApplication.run(ChillvibeApplication.class, args);
  }

}
