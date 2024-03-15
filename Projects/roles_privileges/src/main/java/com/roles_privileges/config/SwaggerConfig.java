package com.roles_privileges.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;

@Component
public class SwaggerConfig {

    @Bean
    public OpenAPI customOpenAPI() {

        return new OpenAPI().info(new Info().title("ROLE & PRIVILEGES").description("role & privileges with jwt security").version("0.0.1"))
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
