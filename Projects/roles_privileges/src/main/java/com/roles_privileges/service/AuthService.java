package com.roles_privileges.service;


import com.roles_privileges.requestDto.LoginRequestDto;
import com.roles_privileges.requestDto.RefreshTokenRequestDto;
import org.springframework.http.ResponseEntity;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface AuthService {

    ResponseEntity<?> getGenerateToken(LoginRequestDto loginRequestDto, HttpServletRequest request, HttpServletResponse response);

    ResponseEntity<?> createRefreshToken(RefreshTokenRequestDto refreshTokenRequestDto, HttpServletResponse response);

}
