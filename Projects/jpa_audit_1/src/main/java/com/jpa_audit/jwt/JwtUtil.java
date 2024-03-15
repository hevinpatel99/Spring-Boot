package com.jpa_audit.jwt;


import com.jpa_audit.config.JWTSecurityConfig;
import com.jpa_audit.dto.ClaimsDto;
import com.jpa_audit.model.CustomUser;
import com.jpa_audit.repository.UserRepository;
import com.jpa_audit.serviceimpl.CustomUserDetailService;
import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.*;


@Component
public class JwtUtil {

    Logger log = LoggerFactory.getLogger(JWTSecurityConfig.class);


    private static final String jwtSecret = "hevinmulanihevinmulanihevinmulanihevinmulanihevinmulanihevinmulani";
    private final UserRepository userRepository;

    public JwtUtil(UserRepository userRepository) {
        this.userRepository = userRepository;
    }


    public String generateToken(Long id, String userName, String role, String password) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("userName", userName);
        claims.put("userId", id);
        claims.put("password", password);
        claims.put("role", role);
        return createToken(claims, userName);
    }

    // generate JWT token
    private String createToken(Map<String, Object> claims, String userName) {

        System.out.println("Claims --- : " + claims);

        Date currentDate = new Date();
        Date expireDate = new Date(currentDate.getTime() + 300000);
        return Jwts.builder()
                .setSubject(userName)
                .setClaims(claims)
                .setIssuedAt(new Date())
                .setExpiration(expireDate)
                .signWith(key())
                .compact();
    }

    private Key key() {
        return Keys.hmacShaKeyFor(
                Decoders.BASE64.decode(jwtSecret)
        );
    }

    Claims getAllClaimsFromToken(String token) {
        return Jwts.parserBuilder().setSigningKey(key()).build().parseClaimsJws(token).getBody();
    }

//    public String getUsername(String token) {
//        Claims claims = Jwts.parserBuilder()
//                .setSigningKey(key())
//                .build()
//                .parseClaimsJws(token)
//                .getBody();
//        String username = claims.getSubject();
//        log.info(" Get User Name : {} ", username);
//        return username;
//    }


    public boolean validateToken(String token) {
        log.info("-- Validate Token -- ");
        try {
            Jwts.parserBuilder()
                    .setSigningKey(key())
                    .build()
                    .parse(token);
            return true;
        } catch (MalformedJwtException | ExpiredJwtException | UnsupportedJwtException | IllegalArgumentException e) {
            log.trace(" Invalid Json Token  : {0} ", e);
        }
        return false;
    }


    public Authentication getAuthentication(String token) {
        Claims allClaimsFromToken = getAllClaimsFromToken(token);
        List<GrantedAuthority> grantedAuthorities = new ArrayList<>();
        String role = (String) allClaimsFromToken.get("role");
        Integer userId = (Integer) allClaimsFromToken.get("userId");
        String userName = (String) allClaimsFromToken.get("userName");
        grantedAuthorities.add(new SimpleGrantedAuthority(role));


        ClaimsDto claimsDto = new ClaimsDto();
        claimsDto.setUserId(Long.valueOf(userId));
        claimsDto.setUserName(userName);
        claimsDto.setPassword((String) allClaimsFromToken.get("password"));

//        UserDetails userDetails = new CustomUser(userId.longValue(), userName, (String) allClaimsFromToken.get("password"), grantedAuthorities);
        return new UsernamePasswordAuthenticationToken(claimsDto, null, grantedAuthorities);
    }

}
