package com.sangjae.restapi.section05.swagger;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI openAPI(){
        return new OpenAPI().info(swaggerInfo());
    }

    private Info swaggerInfo(){
        return new Info().title("Sangjae API")
                .description("Spring Boot Swagger 연동")
                .version("1.0.0");
    }
}
