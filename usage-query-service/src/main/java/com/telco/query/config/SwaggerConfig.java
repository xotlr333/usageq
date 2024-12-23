package com.telco.query.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {
    
    @Bean
    public OpenAPI openAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("사용량 조회 API")
                        .version("1.0")
                        .description("사용자의 음성/영상/문자/데이터 사용량을 조회하는 API 명세서입니다."));
    }
}
