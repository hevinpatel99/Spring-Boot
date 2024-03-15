package com.userrole.jwt;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author Hevin Mulani
 * Filter class to authenticate requests using JWT token.
 */
@Component
public class JwtAuthenticateFilter extends OncePerRequestFilter {

    Logger log = LoggerFactory.getLogger(JwtAuthenticateFilter.class);
    private final JwtUtil jwtUtil;

    public JwtAuthenticateFilter(JwtUtil jwtUtil) {
        this.jwtUtil = jwtUtil;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        log.info(" --- JWT Filter  --- ");

        // get token from the request.
        String token = getTokenFromRequest(request);
        if (StringUtils.hasText(token)) {

            // Validate token
            if (jwtUtil.validateToken(token)) {

                // Retrieve authentication object.
                Authentication auth = jwtUtil.getAuthentication(token);

                // set authentication in securityContextHandler.
                SecurityContextHolder.getContext().setAuthentication(auth);
            }
        }

//            // get username from token
//            Claims claims = jwtUtil.getAllClaimsFromToken(token);
//            String role = (String) claims.get("authorities");
//            log.info(" Claims : {} || {} || {} ", claims.get("userName"), claims.get("userId"), claims.get("authorities"));
//
//
//            // load the user associated with token
//            log.info(" Validate Token status : {}  ", jwtUtil.validateToken(token));
//
//
//            if (jwtUtil.validateToken(token)) {
//                UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(claims, null, role);
//                authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
//                SecurityContextHolder.getContext().setAuthentication(authenticationToken);
//            }

        filterChain.doFilter(request, response);
    }

    /**
     * @return The JWT token extracted from the request header, or null if not found.
     */
    private String getTokenFromRequest(HttpServletRequest request) {

        String bearerToken = request.getHeader("Authorization");

        log.info(" Bearer Token : {} ", bearerToken);

        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer ")) {
            log.info(" Bearer Token Exclude : {} ", bearerToken.substring(7));
            return bearerToken.substring(7);
        }
        return null;
    }
}
