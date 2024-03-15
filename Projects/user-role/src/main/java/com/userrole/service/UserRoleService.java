package com.userrole.service;

import com.userrole.requestDto.UserRoleRequestDto;
import com.userrole.responseDto.ApiResponseDto;
import org.springframework.http.ResponseEntity;


/**
 * @author Hevin Mulani
 * Service interface for user-role related operations.
 */
public interface UserRoleService {
    ResponseEntity<ApiResponseDto> insertUserRole(UserRoleRequestDto roleRequestDto);
}
