package com.jpa_projection.controller;

import com.jpa_projection.model.UserDepartmentMapping;
import com.jpa_projection.model.UserEntity;
import com.jpa_projection.responseDto.ApiResponseDto;
import com.jpa_projection.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    //insert User
    @PostMapping("/insertUser")
    public ResponseEntity<ApiResponseDto> insertUser(@RequestBody UserDepartmentMapping userDepartmentMapping) {
        return userService.insertUser(userDepartmentMapping);
    }

    //get All User
    @GetMapping("/getUser")
    public ResponseEntity<ApiResponseDto> getUser() {
        return userService.getUser();
    }

    //Get User By id
    @GetMapping("/getUser/{id}")
    public ResponseEntity<ApiResponseDto> getUserById(@PathVariable Long id) {
        return userService.getUserById(id);
    }


    @GetMapping("/getUserByFirstName/{firstName}")
    public ResponseEntity<ApiResponseDto> getUserByFirstName(@PathVariable(value = "firstName") String firstName) {
        return userService.getUserByFirstName(firstName);
    }

    @GetMapping("/getUserProjection")
    public ResponseEntity<ApiResponseDto> getAllUserByProjection() {
        return userService.getAllUserByProjection();
    }

    @GetMapping("/getDynamicProjection")
    public ResponseEntity<ApiResponseDto> getDynamicProjection() {
        return userService.getDynamicProjection();
    }

    @GetMapping("/getOpenProjection")
    public ResponseEntity<ApiResponseDto> getOpenProjection() {
        return userService.getOpenProjection();
    }


}
