/*
package com.jpa_audit.controller;


import com.jpa_audit.dto.PermissionDto;
import com.jpa_audit.model.Permission;
import com.jpa_audit.service.PermissionService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

@RestController
@RequestMapping("/permission")
public class PermissionController {

    private final PermissionService permissionService;

    public PermissionController(PermissionService permissionService) {
        this.permissionService = permissionService;
    }

    @PreAuthorize("hasAnyAuthority('SUPER_ADMIN')")
    @GetMapping("/getPermissions")
    public ResponseEntity<?> getAllPermissions() {
        return permissionService.getAllPermissions();
    }


    @PreAuthorize("hasAnyAuthority('SUPER_ADMIN,ADMIN')")
    @PostMapping("/insertPermission")
    public ResponseEntity<?> insertPermission(@RequestBody @Valid PermissionDto permissionDto, HttpServletRequest request) {
        return permissionService.insertPermission(permissionDto, request);
    }

}
*/
