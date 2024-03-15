package com.userrole.jwt;


import com.google.gson.Gson;
import com.userrole.entity.RoleEntity;
import com.userrole.entity.UrlEntity;
import com.userrole.entity.UserEntity;
import com.userrole.exception.CustomException;
import com.userrole.mappings.UrlRoleMapping;
import com.userrole.mappings.UserRoleMapping;
import com.userrole.repository.UrlRepository;
import com.userrole.repository.UrlRoleRepository;
import com.userrole.repository.UserRepository;
import com.userrole.repository.UserRoleRepository;
import com.userrole.response.JwtAuthenticationResponse;
import com.userrole.dto.TokenClaimsDto;
import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;
import com.userrole.requestDto.RefreshTokenRequestDto;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.UncheckedIOException;
import java.security.Key;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static javax.crypto.Cipher.SECRET_KEY;

/**
 * @author Hevin Mulani
 * The JwtUtil is responsible for performing JWT operations like creation and validation.
 */
@Component
@RequiredArgsConstructor
public class JwtUtil {

    Logger log = LoggerFactory.getLogger(JwtUtil.class);

    private static final String jwtSecret = "hevinmulanihevinmulanihevinmulanihevinmulanihevinmulanihevinmulani";
    private static final int jwtExpirationMs = 500000;
    private static final int jwtRefreshExpirationMs = 1000000;

    private final UrlRepository urlRepository;
    private final UrlRoleRepository urlRoleRepository;
    private final UserRepository userRepository;
    private final UserRoleRepository userRoleRepository;

    /**
     * Generate Jwt access & refresh token.
     *
     * @param id
     * @param userName
     * @param roleList
     * @param password
     * @param response
     * @return access & refresh token.
     */
    public JwtAuthenticationResponse generateToken(Long id, String userName, List<String> roleList, String password, HttpServletResponse response) {
        log.info("Roles : {}", roleList);

        // Create a map for put the data in to the claims.
        Map<String, Object> claims = new HashMap<>();
        claims.put("userName", userName);
        claims.put("userId", id);
        claims.put("password", password);
        claims.put("roles", roleList);


        Date currentDate = new Date();
        Date expireDate = new Date(currentDate.getTime() + jwtExpirationMs);
        String accessToken = Jwts.builder().setSubject(userName).setClaims(claims).setIssuedAt(new Date()).setExpiration(expireDate).signWith(key()).compact();

        Date expireRefreshDate = new Date(currentDate.getTime() + jwtRefreshExpirationMs);

        String refreshToken = Jwts.builder().setSubject(userName).setClaims(claims).setIssuedAt(new Date()).setExpiration(expireRefreshDate).signWith(key()).compact();

        response.setHeader("access_token", accessToken);
        response.setHeader("refresh_token", refreshToken);
        return new JwtAuthenticationResponse(accessToken, refreshToken);

    }

    /**
     * Generate key for signing & verify jwt token.
     *
     * @return key
     */
    private Key key() {
        return Keys.hmacShaKeyFor(Decoders.BASE64.decode(jwtSecret));
    }

    /**
     * extract all claims  from jwt token.
     *
     * @param token
     * @return token claims
     */
    public Claims getAllClaimsFromToken(String token) {
        return Jwts.parserBuilder().setSigningKey(key()).build().parseClaimsJws(token).getBody();
    }


    /**
     * retrieve username from jwt token.
     *
     * @param token
     * @return username
     */
    public String getUserNameFromJwtToken(String token) {
        Claims allClaimsFromToken = getClaims(token);
        return (String) allClaimsFromToken.get("userName");
    }


    /**
     * retrieve password from jwt token.
     *
     * @param token
     * @return password
     */
    public String getPasswordFromJwtToken(String token) {
        Claims allClaimsFromToken = getClaims(token);
        return (String) allClaimsFromToken.get("password");
    }


    /**
     * retrieve token claims from jwt token.
     *
     * @param token
     * @return token claims
     */
    private Claims getClaims(String token) {
        return getAllClaimsFromToken(token);
    }


    /**
     * retrieve userId from jwt token.
     *
     * @param token
     * @return userId
     */
    public Integer getUserIdFromJwtToken(String token) {
        Claims allClaimsFromToken = getClaims(token);
        return (Integer) allClaimsFromToken.get("userId");
    }

    /**
     * retrieve roles from jwt token.
     *
     * @param token
     * @return roleList
     */
    public List<String> getRolesFromJwtToken(String token) {
        Claims allClaimsFromToken = getClaims(token);
        return (List<String>) allClaimsFromToken.get("roles", List.class);
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

    /**
     * Validate token
     *
     * @param token
     * @return boolean
     */
    public boolean validateToken(String token) {
        log.info("-- Validate Token -- ");
        try {
            Jwts.parserBuilder().setSigningKey(key()).build().parse(token);
            return true;
        } catch (MalformedJwtException | ExpiredJwtException | UnsupportedJwtException | IllegalArgumentException e) {
            log.trace(" Invalid Json Token  : {0} ", e);
        }
        return false;
    }


    /**
     * set claims & authorities in authentication.
     *
     * @param token
     * @return authentication object
     */
    public Authentication getAuthentication(String token) {
        String userName = getUserNameFromJwtToken(token);
        Integer userId = getUserIdFromJwtToken(token);
        String password = getPasswordFromJwtToken(token);
        List<GrantedAuthority> authorities = getGrantedAuthorities(token);


        log.info("Granted Authorities : {} ", authorities);
        TokenClaimsDto claimsDto = new TokenClaimsDto();
        claimsDto.setUserId(userId.longValue());
        claimsDto.setUserName(userName);
        claimsDto.setPassword(password);
        claimsDto.setGrantedAuthorities(authorities);
//        UserDetails userDetails = new CustomUser(userId.longValue(), userName, (String) allClaimsFromToken.get("password"), grantedAuthorities);
        return new UsernamePasswordAuthenticationToken(claimsDto, null, authorities);
    }


    /**
     * retrieve authorities from jwt token.
     *
     * @param token
     * @return authorities
     */
    public List<GrantedAuthority> getGrantedAuthorities(String token) {
        List<String> roles = getRolesFromJwtToken(token);
        List<GrantedAuthority> authorities = new ArrayList<>();
        // Convert each role to a SimpleGrantedAuthority and add to the list
        for (String role : roles) {
            authorities.add(new SimpleGrantedAuthority(role));
        }
        return authorities;
    }


    /**
     * Generate refresh token.
     *
     * @param refreshTokenRequest
     * @param response
     * @return token
     */
    public JwtAuthenticationResponse createRefreshToken(RefreshTokenRequestDto refreshTokenRequest, HttpServletResponse response) {
        log.info(" -- createRefreshToken :::::: ");
        String refreshToken = refreshTokenRequest.getRequestToken();
        String userNameFromJwtToken = getUserNameFromJwtToken(refreshToken);
        Integer userIdFromJwtToken = getUserIdFromJwtToken(refreshToken);
        String passwordFromJwtToken = getPasswordFromJwtToken(refreshToken);
        List<String> rolesFromJwtToken = getRolesFromJwtToken(refreshToken);
        log.info("id {} || userName {} :  ", userIdFromJwtToken, userNameFromJwtToken);
        log.info("password {} || roles {} : ", passwordFromJwtToken, rolesFromJwtToken);
        return generateToken(userIdFromJwtToken.longValue(), userNameFromJwtToken, rolesFromJwtToken, passwordFromJwtToken, response);
    }


    /**
     * retrieve permission using url.
     *
     * @param url
     * @return permission list
     */
    public List<String> getPermissionsFromUrl(String url) {
        log.info("getPermissionsFromUrl : {} ", url);
        Optional<UrlEntity> byUrlName = urlRepository.findByUrlName(url);
        if (byUrlName.isPresent()) {
            List<UrlRoleMapping> byUrl = urlRoleRepository.findByUrl(byUrlName.get());
            return byUrl.stream().map(urlRoleMapping -> urlRoleMapping.getRole().getRoleName()).collect(Collectors.toList());
        }
        return Collections.emptyList();
    }

    /**
     * retrieve permission using username.
     *
     * @param userName
     * @return userRoles
     */
    public List<String> getPermissionsFromUserName(String userName) {
        log.info(" getPermissionsFromUserName : {} ", userName);
        UserEntity userEntity = userRepository.findByUserName(userName).orElseThrow(() -> new CustomException("User Not Found", HttpStatus.UNAUTHORIZED));
        log.info("userEntity : {} ", new Gson().toJson(userEntity));
        List<UserRoleMapping> userRoleMappingList = userRoleRepository.findByUser(userEntity);
        log.info("User Role Mapping List : {} ", userRoleMappingList);
        if (userRoleMappingList != null) {
            List<String> userRoles = userRoleMappingList.stream().map(userRoleMapping -> userRoleMapping.getRole().getRoleName()).collect(Collectors.toList());
            log.info("getPermissionsFromUserName user role List : {} ", userRoles);
            return userRoles;
        }
        return Collections.emptyList();
    }
}
