package com.jpa_audit.service;


import com.jpa_audit.dto.LoginDTO;
import com.jpa_audit.response.JwtAuthenticationResponse;
import org.springframework.http.ResponseEntity;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface AuthService {

    ResponseEntity<?> getGenerateToken(LoginDTO loginDTO, HttpServletRequest request, HttpServletResponse response);
}
