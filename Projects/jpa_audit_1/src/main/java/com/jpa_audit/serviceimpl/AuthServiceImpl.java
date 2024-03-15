package com.jpa_audit.serviceimpl;

import com.jpa_audit.dto.LoginDTO;
import com.jpa_audit.jwt.JwtUtil;
import com.jpa_audit.model.Role;
import com.jpa_audit.model.User;
import com.jpa_audit.repository.UserRepository;
import com.jpa_audit.response.ApiResponse;
import com.jpa_audit.response.ErrorResponse;
import com.jpa_audit.response.JwtAuthenticationResponse;
import com.jpa_audit.service.AuthService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class AuthServiceImpl implements AuthService {

    Logger log = LoggerFactory.getLogger(AuthServiceImpl.class);


    private final JwtUtil jwtUtil;

    private final UserRepository userRepository;

    public AuthServiceImpl(JwtUtil jwtUtil,
                           UserRepository userRepository) {

        this.jwtUtil = jwtUtil;
        this.userRepository = userRepository;
    }

    @Override
    public ResponseEntity<?> getGenerateToken(LoginDTO loginDTO, HttpServletRequest request, HttpServletResponse response) {
        try {
//            log.info(" Generate Token : {} ", request.getPathInfo());
//            Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginDTO.getUserName(), loginDTO.getPassword()));
////            UserDetails userDetails = customUserDetailService.loadUserByUsername(loginDTO.getUserName());
//            SecurityContextHolder.getContext().setAuthentication(authentication);

            System.out.println("User :" + loginDTO.getUserName());
            Optional<User> user = userRepository.findByUserName(loginDTO.getUserName());

            String token;
            if (user.isPresent()) {
                Long id = user.get().getId();
                String userName = user.get().getUserName();
                List<String> role = user.get().getRoles().stream().map(Role::getRoleName).collect(Collectors.toList());
                String password = user.get().getPassword();

                log.info(" Password  {}", password);
                BCryptPasswordEncoder bcrypt = new BCryptPasswordEncoder();
                boolean matches = bcrypt.matches(loginDTO.getPassword(), password);
                if (matches) {
                    token = jwtUtil.generateToken(id, userName.trim(), role.get(0), password);
                    log.info("Generate Token :: {} ", token);
                    if (!token.isEmpty()) {
                        log.info("Token Generated....");
                        return ResponseEntity.ok(new JwtAuthenticationResponse(token));
                    }
                } else {
                    log.info("Bad Credentials");
                    return ResponseEntity.ok(new ApiResponse(Boolean.FALSE, HttpStatus.BAD_REQUEST, null, "Bad Credentials"));
                }
            } else {
                log.info("User Not Found");
                return ResponseEntity.ok(new ApiResponse(Boolean.FALSE, HttpStatus.BAD_REQUEST, null, "User not found"));
            }

        } catch (BadCredentialsException e) {
            log.trace(" Exception : {0}  ", e);
        }
        return null;
    }
}
