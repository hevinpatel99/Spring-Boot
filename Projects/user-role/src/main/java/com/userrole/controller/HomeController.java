package com.userrole.controller;


import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Hevin Mulani
 * Controller class for handling home-related endpoints.
 * Requires appropriate authorities for accessing different endpoints.
 */
@RestController
@RequestMapping("/home")
@RequiredArgsConstructor
public class HomeController {

    /**
     * Retrieves the request URI and returns it as a greeting message.
     * Requires appropriate authorities for access.
     *
     * @param httpServletRequest
     * @return The request URI as a greeting message.
     * @PreAuthorize annotation use for the check a permission for the request URls.
     */
    //@PreAuthorize("hasAuthority('SUPER_ADMIN') ||" + "hasAuthority('ADMIN') ||" + "hasAuthority('MANAGER') ||" + "hasAuthority('USER')")
    @GetMapping("/greeting1")
    @PreAuthorize("@urlRoleConfig.checkPermission(#httpServletRequest.requestURI)")
    public String greetings1(HttpServletRequest httpServletRequest) {
        return httpServletRequest.getRequestURI();
    }


    //@PreAuthorize("hasAuthority('SUPER_ADMIN') &&" + "hasAuthority('ADMIN')")
    @PreAuthorize("@urlRoleConfig.checkPermission(#httpServletRequest.requestURI)")
    @GetMapping("/greeting2")
    public String greetings2(HttpServletRequest httpServletRequest) {
        return httpServletRequest.getRequestURI();
    }


    //@PreAuthorize("hasAuthority('ADMIN') &&" + "hasAuthority('MANAGER') &&" + "!hasAuthority('SUPER_ADMIN')")
    @PreAuthorize("@urlRoleConfig.checkPermission(#httpServletRequest.requestURI)")
    @GetMapping("/greeting3")
    public String greetings3(HttpServletRequest httpServletRequest) {
        return httpServletRequest.getRequestURI();
    }


    @GetMapping("/greeting4")
    @PreAuthorize("@urlRoleConfig.checkPermission(#httpServletRequest.requestURI)")
    public String greetings4(HttpServletRequest httpServletRequest) {
        return httpServletRequest.getRequestURI();
    }


    @GetMapping("/greeting5")
    @PreAuthorize("@urlRoleConfig.checkPermission(#httpServletRequest.requestURI)")
    public String greetings5(HttpServletRequest httpServletRequest) {
        return httpServletRequest.getRequestURI();
    }
}
