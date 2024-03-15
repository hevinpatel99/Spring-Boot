package com.userrole.service;


import com.userrole.requestDto.LoginRequestDto;
import com.userrole.requestDto.RefreshTokenRequestDto;
import org.springframework.http.ResponseEntity;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author Hevin Mulani
 * Service interface for authentication operations.
 */
public interface AuthService {

    ResponseEntity<?> generateToken(LoginRequestDto loginRequestDto, HttpServletRequest request, HttpServletResponse response);

    ResponseEntity<?> createRefreshToken(RefreshTokenRequestDto refreshTokenRequestDto, HttpServletResponse response);
}
