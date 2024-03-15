package com.jpa_audit.controller;


import com.jpa_audit.dto.RoleDTO;
import com.jpa_audit.service.RoleService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/role")
public class RoleController {

    private final RoleService roleService;

    public RoleController(RoleService roleService) {
        this.roleService = roleService;
    }


    @PreAuthorize("hasAnyAuthority('SUPER_ADMIN')")
    @GetMapping("/getRoles")
    public ResponseEntity<?> getAllRoles() {
        return roleService.getAllRoles();
    }


        @PreAuthorize("hasAnyAuthority('SUPER_ADMIN,ADMIN')")
    @PostMapping("/insertRole")
    public ResponseEntity<?> insertRole(@RequestBody RoleDTO roleDTO, HttpServletRequest request) {
        return roleService.insertRole(roleDTO, request);
    }

}
