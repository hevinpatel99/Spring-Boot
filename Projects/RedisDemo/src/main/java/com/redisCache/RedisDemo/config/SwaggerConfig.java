package com.redisCache.RedisDemo.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

@Component
public class SwaggerConfig {

    @Bean
    public OpenAPI customOpenAPI() {

        return new OpenAPI().info(
                        new Info()
                                .title("Redis_Demo")
                                .version("0.0.1")
                                .description("Redis_Demo")
                                .termsOfService("http://swagger.io/terms/")
                                .license(new License().name("Apache 2.0").url("http://springdoc.org")));

    }
}
