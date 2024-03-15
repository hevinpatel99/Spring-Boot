package com.userrole.controller;


import com.userrole.requestDto.UserRequestDto;
import com.userrole.responseDto.ApiResponseDto;
import com.userrole.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;


/**
 * @author Hevin Mulani
 * Controller for handling user-related endpoints.
 */
@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    /**
     * Endpoint for inserting user.
     *
     * @param userRequestDto
     * @PreAuthorize only SUPER_ADMIN & ADMIN role is authorized for insert user.
     */
    @PreAuthorize("hasAnyAuthority('SUPER_ADMIN','ADMIN')")
    @PostMapping("/insertUser")
    public ResponseEntity<ApiResponseDto> insertUser(@RequestBody @Valid UserRequestDto userRequestDto) {
        return userService.insertUser(userRequestDto);
    }


    /**
     * Endpoint for retrieve all users.
     */
    @GetMapping("/getAllUser")
    public ResponseEntity<ApiResponseDto> getAllUser() {
        return userService.getAllUser();
    }


}
