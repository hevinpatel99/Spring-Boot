package com.userrole.config;

import com.userrole.dto.TokenClaimsDto;
import com.userrole.exception.CustomException;
import com.userrole.jwt.JwtUtil;
import lombok.RequiredArgsConstructor;
import lombok.ToString;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author Hevin Mulani
 * This class provides functionality to check permissions for a given URL based on the user's roles.
 */
@Component
@RequiredArgsConstructor
@ToString
public class UrlRoleConfig {

    Logger log = LoggerFactory.getLogger(UrlRoleConfig.class);

    private final JwtUtil jwtUtil;

    /**
     * check permissions for a given URL based on the user's roles.
     *
     * @param target
     * @return true if condition true otherwise exception.
     * @throws CustomException
     */
    public boolean checkPermission(String target) throws CustomException {

        log.info("Target URL : {} ", target);

        // Retrieve the permission roles associated with the target URL
        List<String> permissionRoles = jwtUtil.getPermissionsFromUrl(target);
        System.out.println("Permission Roles : " + permissionRoles);

        // Retrieve user information from the authentication token
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        TokenClaimsDto tokenClaimsDto = (TokenClaimsDto) authentication.getPrincipal();
        String userName = tokenClaimsDto.getUserName();

        // Retrieve User roles by userName.
        List<String> userRoles = jwtUtil.getPermissionsFromUserName(userName);
        log.info("Users Roles : {}  ", userRoles);

        // Check if any of the user's roles match with the urls permission roles
//        boolean present = userRoles.stream().map(s -> permissionRoles.stream().filter(s::contains)).findAny().isPresent();
        boolean present = false;
        for (String userRole : userRoles) {
            for (String permissionRole : permissionRoles) {
                if (permissionRole.contains(userRole)) {
                    present = true;
                    break;
                }
            }
            if (present) {
                break;
            }
        }
        // If the user doesn't have permission, throw a CustomException with UNAUTHORIZED status
        if (!present)
            throw new CustomException("Unauthorized Access", HttpStatus.UNAUTHORIZED);

        return true;
    }
}




