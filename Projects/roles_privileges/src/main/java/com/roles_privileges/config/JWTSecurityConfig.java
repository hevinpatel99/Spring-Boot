package com.roles_privileges.config;


import com.roles_privileges.jwt.JwtAuthenticateFilter;
import com.roles_privileges.jwt.JwtAuthenticationEntryPoint;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
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

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
@RequiredArgsConstructor
public class JWTSecurityConfig {
    Logger log = LoggerFactory.getLogger(JWTSecurityConfig.class);


    private final JwtAuthenticateFilter jwt;

    private final JwtAuthenticationEntryPoint authenticationEntryPoint;

//    private final CustomUserDetailService userDetailService;


    /*@Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService((UserDetailsService) userDetailService);
        authProvider.setPasswordEncoder(passwordEncoder());

        return authProvider;
    }*/



    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }


    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        log.info(" --- security filter --- ");


        http.csrf().disable().exceptionHandling(exception -> exception.authenticationEntryPoint(authenticationEntryPoint))
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS));


        http.authorizeRequests().antMatchers("/auth/**").permitAll();
        http.authorizeRequests().anyRequest().authenticated();
//                http.authorizeRequests().antMatchers("/user/**").hasAnyAuthority("ADMIN").anyRequest().authenticated();

        http.authorizeRequests().and().exceptionHandling().accessDeniedPage("/403");

        /*http.authenticationProvider(authenticationProvider());*/

        http.addFilterBefore(jwt, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
        return configuration.getAuthenticationManager();
    }

    @Bean
    public WebSecurityCustomizer webSecurityCustomizer() {
        return web -> web.ignoring().antMatchers(this.getPublicUrls());
    }


    public String[] getPublicUrls() {
        return new String[]{"/**/swagger-ui/**", "/api/auth/**", "/v3/api-docs/**", "/configuration/ui", "/swagger-resources", "/configuration/security", "/swagger-ui.html", "/webjars/**", "/swagger-resources/configuration/ui", "/swagger-resources/configuration/security", "/**/actuator/**"};
    }


}
