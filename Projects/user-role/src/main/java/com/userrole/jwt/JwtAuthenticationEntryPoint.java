package com.userrole.jwt;

import com.userrole.responseDto.ApiResponseDto;
import com.userrole.responseDto.ErrorResponseDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


/**
 * @author Hevin Mulani
 * for handling authentication errors for JWT  based authentication.
 */
@Component
public class JwtAuthenticationEntryPoint implements AuthenticationEntryPoint {
    Logger log = LoggerFactory.getLogger(JwtAuthenticationEntryPoint.class);


    /**
     * @param authException the AuthenticationException thrown when the user is unauthorized
     */
    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) {
        log.error("Unauthorized error: {}", authException.getMessage());
        String requestURI = request.getRequestURI();
        log.info("request URI : {} ", requestURI);

        // Creating a response with unauthorized status and error message
        ResponseEntity.ok(new ApiResponseDto(HttpStatus.UNAUTHORIZED, authException.getMessage(), requestURI));
        //response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Error: Unauthorized");
    }
}
