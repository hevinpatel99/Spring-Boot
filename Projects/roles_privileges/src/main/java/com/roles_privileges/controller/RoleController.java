package com.roles_privileges.controller;

import com.roles_privileges.audit.BaseEntityAudit;
import com.roles_privileges.requestDto.RoleRequestDto;
import com.roles_privileges.requestDto.UserRequestDto;
import com.roles_privileges.responseDto.ApiResponseDto;
import com.roles_privileges.service.RoleService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.io.Serializable;

@RestController
@RequestMapping("/role")
@RequiredArgsConstructor
public class RoleController extends BaseEntityAudit {


    private final RoleService roleService;



    @PreAuthorize("hasAnyAuthority('SUPER_ADMIN')")
    @PostMapping("/insertRole")
    public ResponseEntity<ApiResponseDto> insertRole(@RequestBody @Valid RoleRequestDto roleRequestDto) {
        return roleService.insertRole(roleRequestDto);
    }


    @PreAuthorize("hasAnyAuthority('SUPER_ADMIN','ADMIN')")
    @GetMapping("/getAllRoles")
    public ResponseEntity<ApiResponseDto> getAllRoles() {
        return roleService.getAllRoles();
    }


    @PreAuthorize("hasAnyAuthority('SUPER_ADMIN','ADMIN','USER') &&" + "hasPermission('updateEmployee/{id}','UPDATE')")
    @PutMapping("/updateRole/{id}")
    public ResponseEntity<ApiResponseDto> updateUser(@RequestBody @Valid RoleRequestDto roleRequestDto, @PathVariable Long id) {
        return roleService.updateRole(roleRequestDto, id);
    }


}
