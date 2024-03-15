package com.userrole.controller;

import com.userrole.requestDto.UserRoleRequestDto;
import com.userrole.responseDto.ApiResponseDto;
import com.userrole.service.UserRoleService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Hevin Mulani
 * Controller for handling userRole-related endpoints.
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/userRole")
public class UserRoleController {

    private final UserRoleService userRoleService;

    /**
     * Endpoint for inserting user-role mapping.
     *
     * @param userRoleRequestDto contains Ids of User & role.
     * @PreAuthorize only SUPER_ADMIN role is authorized for insert user-role mapping.
     */
    @PreAuthorize("hasAnyAuthority('SUPER_ADMIN')")
    @PostMapping("/insertUserRole")
    public ResponseEntity<ApiResponseDto> insertUserRole(@RequestBody UserRoleRequestDto userRoleRequestDto) {
        return userRoleService.insertUserRole(userRoleRequestDto);
    }


}
