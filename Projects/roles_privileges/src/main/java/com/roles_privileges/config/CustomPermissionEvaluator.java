package com.roles_privileges.config;

import com.roles_privileges.dto.TokenClaimsDto;
import com.roles_privileges.jwt.JwtUtil;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.access.PermissionEvaluator;
import org.springframework.security.core.Authentication;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.io.Serializable;
import java.util.List;

@Configuration
@RequiredArgsConstructor
public class CustomPermissionEvaluator implements PermissionEvaluator {
    private final JwtUtil jwtUtil;

    @Override
    public boolean hasPermission(Authentication authentication, Object targetDomainObject, Object permission) {

        TokenClaimsDto tokenClaimsDto = (TokenClaimsDto) authentication.getPrincipal();
        System.out.println("authentication = " + authentication);
        System.out.println("targetDomainObject = " + targetDomainObject);
        System.out.println("permission = " + permission);
        System.out.println("authentication.getPrincipal() = " + tokenClaimsDto);
        System.out.println("authentication NAme= " + authentication.getName());


        if (targetDomainObject != null) {
            List<String> ls = jwtUtil.getPermissionsFromUserName(tokenClaimsDto.getUserName());
            return ls.contains(permission);
        }
        return false;
    }

    @Override
    public boolean hasPermission(Authentication authentication, Serializable targetId, String targetType, Object permission) {
        return false;
    }
}
