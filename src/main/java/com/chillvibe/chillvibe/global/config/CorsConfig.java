package com.chillvibe.chillvibe.global.config;

import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

public class CorsConfig implements WebMvcConfigurer {

  @Override
  public void addCorsMappings(CorsRegistry corsRegistry){

    corsRegistry.addMapping("/**")
        .allowedOrigins("http://localhost:3000", "https://kdt-cloud-3-team02-final.elicecoding.com")
        .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS")
        .allowedHeaders("*")
        .allowCredentials(true)
        .maxAge(3600);
  }
}