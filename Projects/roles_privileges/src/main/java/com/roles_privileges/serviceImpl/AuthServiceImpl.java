package com.roles_privileges.serviceImpl;

import com.roles_privileges.jwt.JwtUtil;
import com.roles_privileges.mappings.UserRoleMapping;
import com.roles_privileges.model.RoleEntity;
import com.roles_privileges.model.UserEntity;
import com.roles_privileges.repository.UserRepository;
import com.roles_privileges.repository.UserRoleRepository;
import com.roles_privileges.requestDto.LoginRequestDto;
import com.roles_privileges.requestDto.RefreshTokenRequestDto;
import com.roles_privileges.responseDto.ApiResponseDto;
import com.roles_privileges.responseDto.JwtAuthenticationResponse;
import com.roles_privileges.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor

public class AuthServiceImpl implements AuthService {

    Logger log = LoggerFactory.getLogger(AuthServiceImpl.class);


    private final JwtUtil jwtUtil;

    private final UserRepository userRepository;

    private final UserRoleRepository userRoleRepository;

    @Override
    public ResponseEntity<?> getGenerateToken(LoginRequestDto loginRequestDTO, HttpServletRequest request, HttpServletResponse response) {
        try {
//            log.info(" Generate Token : {} ", request.getPathInfo());
//            Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginRequestDTO.getUserName(), loginRequestDTO.getPassword()));
////            UserDetails userDetails = customUserDetailService.loadUserByUsername(loginRequestDTO.getUserName());
//            SecurityContextHolder.getContext().setAuthentication(authentication);

            System.out.println("User :" + loginRequestDTO.getUserName());
            Optional<UserEntity> userEntity = userRepository.findByUserName(loginRequestDTO.getUserName());


            String token;
            List<String> roleList = new ArrayList<>();
            if (userEntity.isPresent()) {
                Long id = userEntity.get().getId();
                String userName = userEntity.get().getUserName();
                UserRoleMapping byUser = userRoleRepository.findByUser(userEntity.get());
                String roleName = byUser.getRole().getRoleName();
                roleList.add(roleName);
                String password = userEntity.get().getPassword();
//                List<String> roleList = roles.stream().map(RoleEntity::getRoleName).collect(Collectors.toList());

                log.info(" Password  {}", password);
                BCryptPasswordEncoder bcrypt = new BCryptPasswordEncoder();
                boolean matches = bcrypt.matches(loginRequestDTO.getPassword(), password);
                if (matches) {
                    JwtAuthenticationResponse jwtAuthenticationResponse = jwtUtil.generateToken(id, userName.trim(), roleList, password, response);
                    log.info("Generate Token :: {} ", jwtAuthenticationResponse);
                    if (jwtAuthenticationResponse != null) {
                        return ResponseEntity.ok(new JwtAuthenticationResponse(jwtAuthenticationResponse.getAccessToken(), jwtAuthenticationResponse.getRefreshToken()));
                    }
                } else {
                    log.info("Bad Credentials");
                    return ResponseEntity.ok(new ApiResponseDto(HttpStatus.BAD_REQUEST, "Bad Credentials", Collections.EMPTY_LIST));
                }
            } else {
                log.info("User Not Found");
                return ResponseEntity.ok(new ApiResponseDto(HttpStatus.BAD_REQUEST, "Bad Credentials", Collections.EMPTY_LIST));
            }

        } catch (BadCredentialsException e) {
            log.trace(" Exception : {0}  ", e);
        }
        return null;
    }

    @Override
    public ResponseEntity<?> createRefreshToken(RefreshTokenRequestDto refreshTokenRequest, HttpServletResponse response) {
        JwtAuthenticationResponse jwtAuthenticationResponse = jwtUtil.createRefreshToken(refreshTokenRequest, response);
        if (jwtAuthenticationResponse != null) {
            return ResponseEntity.ok(new JwtAuthenticationResponse(jwtAuthenticationResponse.getAccessToken(), jwtAuthenticationResponse.getRefreshToken()));
        } else {
            log.info("Bad Credentials");
            return ResponseEntity.ok(new ApiResponseDto(HttpStatus.BAD_REQUEST, "Bad Credentials", Collections.EMPTY_LIST));
        }
    }

//    @Override
//    public ResponseEntity<?> createRefreshToken(RefreshTokenRequestDto refreshToken) {
//        Optional<RefreshToken> byToken = refreshTokenRepository.findByToken(String.valueOf(refreshToken));
//        if (byToken.isPresent()) {
//            String token = byToken.get().getToken();
//            String newToken = jwtUtil.createRefreshToken(token);
//            return ResponseEntity.ok(new JwtAuthenticationResponse(newToken));
//        }
//        return null;
//    }
}