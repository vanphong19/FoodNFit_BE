package com.vanphong.foodnfitbe.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {
    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("FoodNFitBE")
                        .version("1.0")
                        .description("API documentation for FoodNFit backend"));
    }
}
//
//.components(new Components() //Swagger Security có nút auth,
//                .addSecuritySchemes("bearerAuth", new SecurityScheme()
//                    .type(SecurityScheme.Type.HTTP)
//                    .scheme("bearer")
//                    .bearerFormat("JWT")))
//        .addSecurityItem(new SecurityRequirement().addList("bearerAuth", List.of()))