package com.userrole.serviceImpl;


import com.userrole.entity.UserEntity;
import com.userrole.jwt.JwtUtil;
import com.userrole.requestDto.LoginRequestDto;
import com.userrole.requestDto.RefreshTokenRequestDto;
import com.userrole.response.JwtAuthenticationResponse;
import com.userrole.responseDto.ApiResponseDto;
import lombok.RequiredArgsConstructor;
import com.userrole.mappings.UserRoleMapping;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import com.userrole.repository.UserRepository;
import com.userrole.repository.UserRoleRepository;
import com.userrole.service.AuthService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.*;
import java.util.stream.Collectors;


/**
 * @author Hevin Mulani
 * Implementation of the AuthService interface.
 */
@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    Logger log = LoggerFactory.getLogger(AuthServiceImpl.class);


    private final JwtUtil jwtUtil;

    private final UserRepository userRepository;

    private final UserRoleRepository userRoleRepository;


    /**
     * Generate a token.
     *
     * @param loginRequestDto The DTO contains userName & password to generate token.
     * @param request
     * @param response
     * @return generated access & refresh token.
     */
    @Override
    public ResponseEntity<?> generateToken(LoginRequestDto loginRequestDto, HttpServletRequest request, HttpServletResponse response) {
        try {

            log.info(" User : {} ", loginRequestDto.getUserName());

            // retrieve userEntity is find by the userName.
            Optional<UserEntity> userEntity = userRepository.findByUserName(loginRequestDto.getUserName());
            if (userEntity.isPresent()) {
                Long id = userEntity.get().getId();
                String userName = userEntity.get().getUserName();
                // retrieve userRoleMapping Entity is found by the user entity.
                List<UserRoleMapping> userRoleMappingList = userRoleRepository.findByUser(userEntity.get());

                // Iterate userRoleMappingList using stream API to retrieve roelList.
                List<String> roleList = userRoleMappingList.stream().map(role -> role.getRole().getRoleName()).collect(Collectors.toList());
                log.info("RoleList : {} ", roleList);
                String password = userEntity.get().getPassword();
                log.info(" Password  {}", password);

                // create BCryptPasswordEncoder instance.
                BCryptPasswordEncoder bcrypt = new BCryptPasswordEncoder();

                // Check if the provided password matches the bcrypt password stored in the database
                boolean matches = bcrypt.matches(loginRequestDto.getPassword(), password);

                // If the passwords match, generate a JWT authentication response
                if (matches) {
                    // Generate a JWT token using JwtUtil
                    JwtAuthenticationResponse jwtAuthenticationResponse = jwtUtil.generateToken(id, userName.trim(), roleList, password, response);
                    log.info("Generate Token :: {} ", jwtAuthenticationResponse);
                    if (jwtAuthenticationResponse != null) {

                        // return generated access & refresh token response.
                        return ResponseEntity.ok(new JwtAuthenticationResponse(jwtAuthenticationResponse.getAccessToken(), jwtAuthenticationResponse.getRefreshToken()));
                    }
                } else {

                    // return empty data id token are not generated.
                    log.info("Bad Credentials");
                    return ResponseEntity.ok(new ApiResponseDto(HttpStatus.BAD_REQUEST, "Bad Credentials", Collections.EMPTY_LIST));
                }
            } else {

                // return empty data if user not found in database.
                log.info("User Not Found");
                return ResponseEntity.ok(new ApiResponseDto(HttpStatus.BAD_REQUEST, "Bad Credentials", Collections.EMPTY_LIST));
            }
        } catch (BadCredentialsException e) {
            log.trace(" Exception : {0}  ", e);
        }
        return null;
    }


    /**
     * Create a refresh token.
     *
     * @param refreshTokenRequest The DTO contains refresh token to generate new tokens.
     * @param response
     * @return generated access & refresh token.
     */
    @Override
    public ResponseEntity<?> createRefreshToken(RefreshTokenRequestDto refreshTokenRequest, HttpServletResponse response) {
        // Create a refresh token using JwtUtil
        JwtAuthenticationResponse jwtAuthenticationResponse = jwtUtil.createRefreshToken(refreshTokenRequest, response);
        if (jwtAuthenticationResponse != null) {

            // return generated access & refresh token response.
            return ResponseEntity.ok(new JwtAuthenticationResponse(jwtAuthenticationResponse.getAccessToken(), jwtAuthenticationResponse.getRefreshToken()));
        } else {
            log.info("Bad Credentials");
            // return empty data id token are not generated.
            return ResponseEntity.ok(new ApiResponseDto(HttpStatus.BAD_REQUEST, "Bad Credentials", Collections.EMPTY_LIST));
        }
    }

}