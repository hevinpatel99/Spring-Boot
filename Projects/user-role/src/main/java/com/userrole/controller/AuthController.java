package com.userrole.controller;


import com.userrole.requestDto.LoginRequestDto;
import com.userrole.requestDto.RefreshTokenRequestDto;
import com.userrole.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;


/**
 * @author Hevin Mulani
 * Controller for handling authentication-related endpoints.
 */
@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {
    private final Logger log = LoggerFactory.getLogger(AuthController.class);

    private final AuthService authService;


    /**
     * Endpoint for generating authentication token.
     *
     * @param loginRequestDto containing required credentials
     * @param request
     * @param response
     */
    @PostMapping(value = "/authenticate")
    public ResponseEntity<?> generateToken(@RequestBody @Valid LoginRequestDto loginRequestDto, HttpServletRequest request, HttpServletResponse response) {
        log.info(" -- Get Generate Token -- ");
        return authService.generateToken(loginRequestDto, request, response);
    }

    /**
     * Endpoint for generating refresh token.
     * @param refreshTokenRequestDto containing required user credentials.
     * @param response
     * @return
     */
    @PostMapping("/refreshToken")
    public ResponseEntity<?> createRefreshToken(@RequestBody @Valid RefreshTokenRequestDto refreshTokenRequestDto, HttpServletResponse response) {
        log.info(" -- Generate Refresh Token -- ");
        return authService.createRefreshToken(refreshTokenRequestDto, response);
    }
}



