package com.jpa_audit.controller;


import com.jpa_audit.audit.AuditorAwareImpl;
import com.jpa_audit.dto.LoginDTO;
import com.jpa_audit.response.JwtAuthenticationResponse;
import com.jpa_audit.service.AuthService;
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
public class AuthController {
    private  final Logger log = LoggerFactory.getLogger(AuditorAwareImpl.class);

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping(value = "/authenticate")
    public ResponseEntity<?> getGenerateToken(@RequestBody @Valid LoginDTO loginDTO, HttpServletRequest request, HttpServletResponse response) {
        log.info("Get Generate Token");
        return authService.getGenerateToken(loginDTO, request, response);

//        ResponseEntity<?> jwtAuthenticationResponse =
//        model.addAttribute("jwtAuthenticationResponse", jwtAuthenticationResponse);
//        return "view";

    }


}
