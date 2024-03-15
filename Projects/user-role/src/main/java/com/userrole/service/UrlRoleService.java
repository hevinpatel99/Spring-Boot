package com.userrole.service;

import com.userrole.requestDto.UrlRoleRequestDto;
import com.userrole.responseDto.ApiResponseDto;
import org.springframework.http.ResponseEntity;


/**
 * @author Hevin Mulani
 * Service interface for url-role related operations.
 */
public interface UrlRoleService {
    ResponseEntity<ApiResponseDto> insertUrlRole(UrlRoleRequestDto urlRoleRequestDto);
}
