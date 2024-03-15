package com.userrole.service;


import com.userrole.requestDto.RoleRequestDto;
import com.userrole.responseDto.ApiResponseDto;
import org.springframework.http.ResponseEntity;


/**
 * @author Hevin Mulani
 * Service interface for role related operations.
 */
public interface RoleService {
    ResponseEntity<ApiResponseDto> insertRole(RoleRequestDto roleRequestDto);

    ResponseEntity<ApiResponseDto> getAllRoles();


}
