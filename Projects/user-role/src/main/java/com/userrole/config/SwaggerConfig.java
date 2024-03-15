package com.userrole.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;

/**
 * @author Hevin Mulani
 * Configuration class for Swagger documentation.
 */
@Component
public class SwaggerConfig {


    /**
     * Provides custom OpenAPI configuration.
     *
     * @return representing the API documentation.
     */
    @Bean
    public OpenAPI customOpenAPI() {

        return new OpenAPI().info(new Info().title("USER & ROLES").description("user & roles with jwt security").version("0.0.1"))
                .schemaRequirement(
                        HttpHeaders.AUTHORIZATION,
                        new SecurityScheme()
                                .type(SecurityScheme.Type.HTTP)
                                .scheme("Bearer")
                                .description("from authenticate api")
                                .name(HttpHeaders.AUTHORIZATION))
                .addSecurityItem(
                        new SecurityRequirement()
                                .addList(HttpHeaders.AUTHORIZATION, HttpHeaders.AUTHORIZATION));
    }
}
