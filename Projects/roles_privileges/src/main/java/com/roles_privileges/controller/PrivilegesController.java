package com.roles_privileges.controller;

import com.roles_privileges.requestDto.PrivilegeRequestDto;
import com.roles_privileges.responseDto.ApiResponseDto;
import com.roles_privileges.service.PrivilegesService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/Privilege")
@RequiredArgsConstructor
public class PrivilegesController {

    private final PrivilegesService privilegesService;

    @PreAuthorize("hasAnyAuthority('SUPER_ADMIN') &&" + "hasPermission('insertPrivilege','WRITE')")
    @PostMapping("/insertPrivilege")
    public ResponseEntity<ApiResponseDto> insertUser(@RequestBody @Valid PrivilegeRequestDto privilegeRequestDto) {
        return privilegesService.insertPrivilege(privilegeRequestDto);
    }


    @PreAuthorize("hasAnyAuthority('SUPER_ADMIN','ADMIN') &&" + "hasPermission('getAllPrivilege','READ')")
    @PostMapping("/getAllPrivilege")
    public ResponseEntity<ApiResponseDto> getAllPrivilege() {
        return privilegesService.getAllPrivilege();
    }

}
