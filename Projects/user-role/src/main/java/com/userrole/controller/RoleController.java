package com.userrole.controller;


import com.userrole.requestDto.RoleRequestDto;
import com.userrole.responseDto.ApiResponseDto;
import com.userrole.service.RoleService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * @author Hevin Mulani
 * Controller class for handling role-related endpoints.
 */
@RestController
@RequestMapping("/role")
@RequiredArgsConstructor
public class RoleController {


    private final RoleService roleService;


    /**
     * Endpoint for inserting role.
     *
     * @param roleRequestDto
     * @PreAuthorize only SUPER_ADMIN & ADMIN role is authorized for insert user
     */
    @PreAuthorize("hasAnyAuthority('SUPER_ADMIN','ADMIN')")
    @PostMapping("/insertRole")
    public ResponseEntity<ApiResponseDto> insertRole(@RequestBody @Valid RoleRequestDto roleRequestDto) {
        return roleService.insertRole(roleRequestDto);
    }

    /**
     * Endpoint for retrieve all roles.
     * @PreAuthorize
     */
    @PreAuthorize("hasAnyAuthority('SUPER_ADMIN','ADMIN')")
    @GetMapping("/getAllRoles")
    public ResponseEntity<ApiResponseDto> getAllRoles() {
        return roleService.getAllRoles();
    }

}
