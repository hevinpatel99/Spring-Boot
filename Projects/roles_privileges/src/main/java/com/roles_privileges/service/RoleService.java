package com.roles_privileges.service;

import com.roles_privileges.requestDto.RoleRequestDto;
import com.roles_privileges.responseDto.ApiResponseDto;
import org.springframework.http.ResponseEntity;

public interface RoleService {
    ResponseEntity<ApiResponseDto> insertRole(RoleRequestDto roleRequestDto);

    ResponseEntity<ApiResponseDto> getAllRoles();

    ResponseEntity<ApiResponseDto> updateRole(RoleRequestDto roleRequestDto, Long id);
}
