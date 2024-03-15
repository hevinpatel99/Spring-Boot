package com.userrole.config;

import com.userrole.exception.CustomException;
import com.userrole.jwt.JwtAuthenticateFilter;
import com.userrole.jwt.JwtAuthenticationEntryPoint;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.client.HttpClientErrorException;

import javax.security.sasl.AuthorizeCallback;
import javax.servlet.http.HttpServletRequest;


/**
 * @author Hevin Mulani
 * Configuration class for JWT-based security settings.
 * This class defines the security configurations for JWT authentication and authorization.
 */
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
@RequiredArgsConstructor
public class JWTSecurityConfig {
    Logger log = LoggerFactory.getLogger(JWTSecurityConfig.class);


    private final JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;
    private final JwtAuthenticateFilter jwtAuthenticateFilter;


    /**
     * Defines a bean for password encoder.
     *
     * @return An instance of BCryptPasswordEncoder.
     */
    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    /**
     * Configures the security filter chain.
     *
     * @param http
     * @return Configured securityFilterChain.
     * @throws Exception
     */
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        log.info(" --- security filter --- ");

        // Disables CSRF protection and sets up exception handling for JWT authentication
        http.csrf().disable().exceptionHandling(exception -> exception.authenticationEntryPoint(jwtAuthenticationEntryPoint))
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS));

        // Allows access to "/auth/**" endpoints without authentication
        http.authorizeRequests().antMatchers("/auth/**").permitAll();

        // Requires authentication for any other endpoint
        http.authorizeRequests().anyRequest().authenticated();

        // Add JWT authentication filter.
        http.addFilterBefore(jwtAuthenticateFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }


    /**
     * Define a bean for authentication manager.
     *
     * @param authenticationConfiguration
     * @return configured aurthenticationManager
     * @throws Exception
     */
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }


    /**
     * Configures web security by ignoring public URLs.
     *
     * @return A WebSecurityCustomizer for configuring web security.
     */
    @Bean
    public WebSecurityCustomizer webSecurityCustomizer() {
        return web -> web.ignoring().antMatchers(this.getPublicUrls());
    }

    /**
     * Retrieves an array of public URLs to be ignored by web security.
     *
     * @return array of public Urls
     */
    public String[] getPublicUrls() {
        return new String[]{"/**/swagger-ui/**", "/api/auth/**", "/v3/api-docs/**", "/configuration/ui", "/swagger-resources", "/configuration/security", "/swagger-ui.html", "/webjars/**", "/swagger-resources/configuration/ui", "/swagger-resources/configuration/security", "/**/actuator/**"};
    }


}
