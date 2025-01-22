package com.lec.spring.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.nio.file.Path;
import java.nio.file.Paths;

@Configuration
public class JsonConfig {
    @Bean
    public Path hbtiDataPath() {
        // JSON 파일의 경로를 설정합니다.
        return Paths.get("src/main/resources/static/data/HbtiData.json");
    }
}
