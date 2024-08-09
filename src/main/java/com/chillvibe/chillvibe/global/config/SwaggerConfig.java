package com.chillvibe.chillvibe.global.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {
  @Bean
  public OpenAPI openAPI() {
    // JWT 보안 요구사항 추가
    String jwt = "JWT";
    SecurityRequirement securityRequirement = new SecurityRequirement().addList(jwt);

    // JWT 보안 스키마 설정
    Components components = new Components().addSecuritySchemes(jwt, new SecurityScheme()
        .name(jwt)
        .type(SecurityScheme.Type.HTTP)
        .scheme("bearer")
        .bearerFormat("JWT")
    );

    return new OpenAPI()
        .components(new Components())
        .info(apiInfo())
        .addSecurityItem(securityRequirement)
        .components(components);
  }
  private Info apiInfo() {
    return new Info()
        .title("ChillVibe")
        .description("REST API for ChillVibe application")
        .version("1.0.0");
  }
}

