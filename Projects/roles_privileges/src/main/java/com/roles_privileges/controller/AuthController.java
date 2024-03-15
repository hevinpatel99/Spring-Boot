package com.roles_privileges.controller;


import com.roles_privileges.requestDto.LoginRequestDto;
import com.roles_privileges.requestDto.RefreshTokenRequestDto;
import com.roles_privileges.service.AuthService;
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

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {
    private final Logger log = LoggerFactory.getLogger(AuthController.class);

    private final AuthService authService;

    @PostMapping(value = "/authenticate")
    public ResponseEntity<?> getGenerateToken(@RequestBody @Valid LoginRequestDto loginRequestDto, HttpServletRequest request, HttpServletResponse response) {
        log.info("Get Generate Token");
        return authService.getGenerateToken(loginRequestDto, request, response);

//        ResponseEntity<?> jwtAuthenticationResponse =
//        model.addAttribute("jwtAuthenticationResponse", jwtAuthenticationResponse);
//        return "view";

    }

    @PostMapping("/refreshToken")
    public ResponseEntity<?> refreshToken(@RequestBody @Valid RefreshTokenRequestDto refreshTokenRequestDto, HttpServletResponse response) {
        log.info(" Generate Refresh Token");
        return authService.createRefreshToken(refreshTokenRequestDto,response);
    }
}



