package com.cafe24.entertainment.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration // 설정 파일임을 알림
@Slf4j
public class ApiConfig implements WebMvcConfigurer {

  // CORS
  @Override
  public void addCorsMappings(CorsRegistry registry) {
    registry.addMapping("/**").allowedOrigins("http://localhost:3000");
  }
}
