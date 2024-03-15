package com.userrole.controller;

import com.userrole.requestDto.UrlRoleRequestDto;
import com.userrole.responseDto.ApiResponseDto;
import com.userrole.service.UrlRoleService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Hevin Mulani
 * Controller for handling UrlRole-related endpoints.
 */
@RestController
@RequestMapping("/urlRole")
@RequiredArgsConstructor
public class UrlRoleController {

    private final UrlRoleService urlRoleService;

    /**
     * Endpoint for inserting url-role mapping.
     *
     * @param urlRoleRequestDto contains Ids of Url & role.
     */
    @PostMapping("/insertUrlRole")
    public ResponseEntity<ApiResponseDto> insertUrlRole(@RequestBody UrlRoleRequestDto urlRoleRequestDto) {
        return urlRoleService.insertUrlRole(urlRoleRequestDto);
    }
}
