package com.usage.query.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {
    @Bean
    public OpenAPI usageOpenAPI() {
        return new OpenAPI()
            .info(new Info()
                .title("사용량 조회 API")
                .description("사용자의 통신 사용량을 조회하는 API입니다.")
                .version("1.0.0"));
    }
}
