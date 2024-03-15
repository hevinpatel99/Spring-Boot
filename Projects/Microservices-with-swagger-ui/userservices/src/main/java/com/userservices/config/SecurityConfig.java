package com.userservices.config;


import org.springframework.context.annotation.Configuration;

@Configuration
//@EnableWebSecurity
//@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig {


//    @Bean
//    public SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception {
//        httpSecurity.authorizeHttpRequests().antMatchers("/swagger-ui**/**","/v3/api-docs/**").permitAll().anyRequest().authenticated().and().oauth2ResourceServer().jwt();
//        return httpSecurity.build();
//    }
}
