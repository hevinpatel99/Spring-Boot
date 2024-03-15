package com.roles_privileges.controller;


import com.roles_privileges.model.UserEntity;
import com.roles_privileges.requestDto.UserRequestDto;
import com.roles_privileges.responseDto.ApiResponseDto;
import com.roles_privileges.responseDto.UserResponseDto;
import com.roles_privileges.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {


    private final UserService userService;


    //    @PreAuthorize("hasAnyAuthority('SUPER_ADMIN')")
    @PreAuthorize("hasAnyAuthority('SUPER_ADMIN','ADMIN') &&" +
            "hasPermission('insertUser', 'WRITE')")
    @PostMapping("/insertUser")
    public ResponseEntity<ApiResponseDto> insertUser(@RequestBody @Valid UserRequestDto userRequestDto) {
        return userService.insertUser(userRequestDto);
    }

    @PreAuthorize("hasAnyAuthority('SUPER_ADMIN','ADMIN','USER') && " + "hasPermission('getAllUser','READ')")
   /* @PreAuthorize("hasAnyAuthority('SUPER_ADMIN') &&" +
            " hasPermission('getAllUser', 'READ') || hasPermission('getAllUser', 'WRITE')" +
            "|| hasPermission('getAllUser', 'UPDATE')")*/
    @GetMapping("/getAllUser")
    public ResponseEntity<ApiResponseDto> getAllUser() {
        return userService.getAllUser();
    }


    @PreAuthorize("hasAnyAuthority('SUPER_ADMIN','ADMIN','USER') &&" + "hasPermission('getUser/{id}','READ')")
    @GetMapping("/getUser/{id}")
    public ResponseEntity<ApiResponseDto> getUserById(@PathVariable Long id) {
        return userService.getUserById(id);
    }

    @PreAuthorize("hasAnyAuthority('SUPER_ADMIN','ADMIN','USER') &&" + "hasPermission('updateEmployee/{id}','UPDATE')")
    @PutMapping("/updateEmployee/{id}")
    public ResponseEntity<ApiResponseDto> updateUser(@RequestBody @Valid UserRequestDto userDto, @PathVariable Long id) {
        return userService.updateUser(userDto, id);
    }

    @PreAuthorize("hasAnyAuthority('SUPER_ADMIN','ADMIN','USER')  &&" + "hasPermission('changeStatus/{id}','UPDATE')")
    @DeleteMapping("/changeStatus/{id}")
    public ResponseEntity<ApiResponseDto> changeStatus(@PathVariable Long id) {
        return userService.changeStatus(id);
    }

    @PreAuthorize("hasAnyAuthority('SUPER_ADMIN','ADMIN') && " + "hasPermission('remove/{id}','DELETE')")
    @DeleteMapping("/remove/{id}")
    public ResponseEntity<ApiResponseDto> removeUser(@PathVariable Long id) {
        return userService.removeUser(id);
    }


}
