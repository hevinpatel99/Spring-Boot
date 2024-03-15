package com.roles_privileges.service;

import com.roles_privileges.requestDto.PrivilegeRequestDto;
import com.roles_privileges.responseDto.ApiResponseDto;
import org.springframework.http.ResponseEntity;

public interface PrivilegesService {
    ResponseEntity<ApiResponseDto> insertPrivilege(PrivilegeRequestDto privilegeRequestDto);

    ResponseEntity<ApiResponseDto> getAllPrivilege();
}
