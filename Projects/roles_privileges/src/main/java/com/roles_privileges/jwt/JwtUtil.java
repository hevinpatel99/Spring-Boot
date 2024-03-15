package com.roles_privileges.jwt;


import com.google.gson.Gson;
import com.roles_privileges.dto.TokenClaimsDto;
import com.roles_privileges.exception.CustomException;
import com.roles_privileges.mappings.RolePrivilegeMapping;
import com.roles_privileges.mappings.UserRoleMapping;
import com.roles_privileges.model.RoleEntity;
import com.roles_privileges.model.UserEntity;
import com.roles_privileges.repository.RolePrivilegesRepository;
import com.roles_privileges.repository.RoleRepository;
import com.roles_privileges.repository.UserRepository;
import com.roles_privileges.repository.UserRoleRepository;
import com.roles_privileges.requestDto.RefreshTokenRequestDto;
import com.roles_privileges.responseDto.JwtAuthenticationResponse;
import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletResponse;
import java.security.Key;
import java.util.*;
import java.util.stream.Collectors;

import static java.util.Arrays.sort;
import static java.util.Arrays.stream;


@Component
public class JwtUtil {

    Logger log = LoggerFactory.getLogger(JwtUtil.class);


    private static final String jwtSecret = "hevinmulanihevinmulanihevinmulanihevinmulanihevinmulanihevinmulani";
    private static final int jwtExpirationMs = 500000;
    private static final int jwtRefreshExpirationMs = 1000000;
    private final RoleRepository roleRepository;

    private final UserRepository userRepository;

    private final UserRoleRepository userRoleRepository;

    private final RolePrivilegesRepository rolePrivilegesRepository;

    public JwtUtil(RoleRepository roleRepository, UserRepository userRepository, UserRoleRepository userRoleRepository, RolePrivilegesRepository rolePrivilegesRepository) {
        this.roleRepository = roleRepository;
        this.userRepository = userRepository;
        this.userRoleRepository = userRoleRepository;
        this.rolePrivilegesRepository = rolePrivilegesRepository;
    }


    public JwtAuthenticationResponse generateToken(Long id, String userName, List<String> roleList, String password, HttpServletResponse response) {
//        List<String> roleList = roles.stream().map(Role::getRoleName).collect(Collectors.toList());
        log.info("Roles : {}", roleList);

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

    // generate JWT token
/*
    private JwtAuthenticationResponse createToken(Map<String, Object> claims, String userName, HttpServletResponse response) {

        log.info("Claims : {}", claims);
        Date currentDate = new Date();
        Date expireDate = new Date(currentDate.getTime() + jwtExpirationMs);
        String accessToken = Jwts.builder()
                .setSubject(userName)
                .setClaims(claims)
                .setIssuedAt(new Date())
                .setExpiration(expireDate)
                .signWith(key())
                .compact();

        Date expireRefreshDate = new Date(currentDate.getTime() + jwtRefreshExpirationMs);

        String refreshToken = Jwts.builder()
                .setSubject(userName)
                .setClaims(claims)
                .setIssuedAt(new Date())
                .setExpiration(expireRefreshDate)
                .signWith(key())
                .compact();

        response.setHeader("access_token", accessToken);
        response.setHeader("refresh_token", refreshToken);
        return new JwtAuthenticationResponse(accessToken, refreshToken);

    }
*/

   /* public String generateRefreshToken(Long id, String userName, List<String> roleList, String password) {

        log.info("Roles : {}", roleList);

        Map<String, Object> claims = new HashMap<>();
        claims.put("userName", userName);
        claims.put("userId", id);
        claims.put("password", password);
        claims.put("roles", roleList);
        return createToken(claims, userName, jwtRefreshExpirationMs);
    }*/


    private Key key() {
        return Keys.hmacShaKeyFor(Decoders.BASE64.decode(jwtSecret));
    }

    public Claims getAllClaimsFromToken(String token) {
        return Jwts.parserBuilder().setSigningKey(key()).build().parseClaimsJws(token).getBody();
    }


    public String getUserNameFromJwtToken(String token) {
        Claims allClaimsFromToken = getClaims(token);
        return (String) allClaimsFromToken.get("userName");
    }

    public String getPasswordFromJwtToken(String token) {
        Claims allClaimsFromToken = getClaims(token);
        return (String) allClaimsFromToken.get("password");
    }

    private Claims getClaims(String token) {
        return getAllClaimsFromToken(token);
    }

    public Integer getUserIdFromJwtToken(String token) {
        Claims allClaimsFromToken = getClaims(token);
        return (Integer) allClaimsFromToken.get("userId");
    }

    @SuppressWarnings("unchecked")
    public List<String> getRolesFromJwtToken(String token) {
        Claims allClaimsFromToken = getClaims(token);
        return (List<String>) allClaimsFromToken.get("roles");
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
            Jwts.parserBuilder().setSigningKey(key()).build().parse(token);
            return true;
        } catch (MalformedJwtException | ExpiredJwtException | UnsupportedJwtException | IllegalArgumentException e) {
            log.trace(" Invalid Json Token  : {0} ", e);
        }
        return false;
    }


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
        return new UsernamePasswordAuthenticationToken(claimsDto, null, authorities);
    }

    public List<GrantedAuthority> getGrantedAuthorities(String token) {
        List<String> roles = getRolesFromJwtToken(token);
        List<GrantedAuthority> authorities = new ArrayList<>();
        // Convert each role to a SimpleGrantedAuthority and add to the list
        for (String role : roles) {
            authorities.add(new SimpleGrantedAuthority(role));
            Optional<RoleEntity> roleName = roleRepository.findByRoleNameIgnoreCase(role);
            if (roleName.isPresent()) {
                RoleEntity roleEntity = roleName.get();
                List<RolePrivilegeMapping> rolePrivilegeList = this.rolePrivilegesRepository.findByRoleEntity(roleEntity);
                for (RolePrivilegeMapping rolePrivilegeMapping : rolePrivilegeList) {
                    authorities.add(new SimpleGrantedAuthority(rolePrivilegeMapping.getPrivilegeEntity().getPrivilegeName()));
                }
            }
        }
        System.out.println("Role With Privilege : " + authorities);
        return authorities;
    }

    public JwtAuthenticationResponse createRefreshToken(RefreshTokenRequestDto refreshTokenRequest, HttpServletResponse response) {
        System.out.println(" :::::::: createRefreshToken :::::: ");
        String refreshToken = refreshTokenRequest.getRequestToken();
        String userNameFromJwtToken = getUserNameFromJwtToken(refreshToken);
        Integer userIdFromJwtToken = getUserIdFromJwtToken(refreshToken);
        String passwordFromJwtToken = getPasswordFromJwtToken(refreshToken);
        List<String> rolesFromJwtToken = getRolesFromJwtToken(refreshToken);
        System.out.println("id: " + userIdFromJwtToken + "Username : " + userNameFromJwtToken);
        System.out.println("password: " + passwordFromJwtToken + "roles : " + rolesFromJwtToken);
        return generateToken(userIdFromJwtToken.longValue(), userNameFromJwtToken, rolesFromJwtToken, passwordFromJwtToken, response);
    }

    public List<String> getPermissionsFromUserName(String userName) {
        System.out.println("JWT --> getPermissionsFromUserName:: " + userName);
        UserEntity userEntity = userRepository.findByUserName(userName).orElseThrow(() -> new CustomException("User Not Found", HttpStatus.UNAUTHORIZED));
        log.info("userEntity ::{}", new Gson().toJson(userEntity));
        UserRoleMapping userRoleMapping = userRoleRepository.findByUser(userEntity);
        System.out.println("User Role Mapping:" + userRoleMapping);
        if (userRoleMapping != null) {
            List<RolePrivilegeMapping> rolePrivilegeMappingList = rolePrivilegesRepository.findByRoleEntity(userRoleMapping.getRole());
            List<String> permissionList = rolePrivilegeMappingList.stream().map(p -> p.getPrivilegeEntity().getPrivilegeName()).collect(Collectors.toList());
            System.out.println("Permission List: " + permissionList);
            return permissionList;
        }
        return Collections.emptyList();
    }


}
