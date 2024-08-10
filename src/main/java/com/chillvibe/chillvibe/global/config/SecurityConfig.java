package com.chillvibe.chillvibe.global.config;

import com.chillvibe.chillvibe.domain.user.repository.UserRepository;
import com.chillvibe.chillvibe.global.jwt.filter.CustomLogoutFilter;
import com.chillvibe.chillvibe.global.jwt.filter.JwtFilter;
import com.chillvibe.chillvibe.global.jwt.filter.LoginFilter;
import com.chillvibe.chillvibe.global.jwt.repository.RefreshRepository;
import com.chillvibe.chillvibe.global.jwt.util.JwtUtil;
import jakarta.servlet.http.HttpServletRequest;
import java.util.Collections;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.logout.LogoutFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

  private static final String[] WHITER_LIST = {
      "/login",
      "/logout",
      "/api/reissue",
      "/api/signup",
      "/api/userpage",
      "/api/search**",
      "/api/comments/byPost",
      "/api/comments/byUser",
      "/api/hashtags/**",
      "/api/posts/**",
      "/api/tracks/**",
      /* Swagger UI */
      "/swagger-ui/**",
      "/v3/api-docs/**",
  };


  private final AuthenticationConfiguration authenticationConfiguration;
  private final JwtUtil jwtUtil;
  private final RefreshRepository refreshRepository;
  private final UserRepository userRepository;

  @Bean
  public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration)
      throws Exception {

    return configuration.getAuthenticationManager();
  }

  // 비밀번호 암호화
  @Bean
  public BCryptPasswordEncoder bCryptPasswordEncoder() {
    return new BCryptPasswordEncoder();
  }

  // 필터 체인 구축
  @Bean
  public SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception {

    // cors 설정
    httpSecurity
        .cors((cors) -> cors
            .configurationSource(new CorsConfigurationSource() {
              @Override
              public CorsConfiguration getCorsConfiguration(HttpServletRequest request) {

                CorsConfiguration configuration = new CorsConfiguration();

                configuration.setAllowedOrigins(Collections.singletonList("http://localhost:3000"));
                configuration.setAllowedMethods(Collections.singletonList("*"));
                configuration.setAllowCredentials(true);
                configuration.setAllowedHeaders(Collections.singletonList("*"));
                configuration.setMaxAge(3600L);

                configuration.setExposedHeaders(Collections.singletonList("Authorization"));

                return configuration;
              }
            }));

    httpSecurity
        .csrf(AbstractHttpConfigurer::disable);

    // From 로그인 방식 disable
    httpSecurity
        .formLogin(AbstractHttpConfigurer::disable);

    // http basic 인증 방식 disable
    httpSecurity
        .httpBasic(AbstractHttpConfigurer::disable);

//    // 스프링 시큐리티 로그인 기본 엔드포인트 커스텀
//    httpSecurity
//        .formLogin(form -> form
//            .loginProcessingUrl("/api/login")
//            .permitAll());
//
//    // 스프링 시큐리티 로그아웃 기본 엔드포인트 커스텀
//    httpSecurity
//        .logout(logout -> logout
//            .logoutUrl("/api/logout")
//            .logoutSuccessUrl("/")
//            .permitAll());

    // 경로별 인가 작업
    httpSecurity
        .authorizeHttpRequests((auth) -> auth
            .requestMatchers(WHITER_LIST).permitAll()
            .requestMatchers("/reissue").permitAll()
            .anyRequest().authenticated());

    // JwtFilter 등록
    // LoginFilter 앞에 필터 장착
    httpSecurity
        .addFilterBefore(new JwtFilter(jwtUtil), LoginFilter.class);

    // LoginFilter 등록
    // UsernamePasswordAuthenticationFilter 자리에 필터 장착
    httpSecurity
        .addFilterAt(new LoginFilter(authenticationManager(authenticationConfiguration), jwtUtil,
            refreshRepository, userRepository), UsernamePasswordAuthenticationFilter.class);

    // CustomLogoutFilter 등록
    // LogoutFilter 앞에 필터 장착
    httpSecurity
        .addFilterBefore(new CustomLogoutFilter(jwtUtil, refreshRepository), LogoutFilter.class);

    //세션 설정
    httpSecurity
        .sessionManagement((session) -> session
            .sessionCreationPolicy(SessionCreationPolicy.STATELESS));

    return httpSecurity.build();
  }
}
