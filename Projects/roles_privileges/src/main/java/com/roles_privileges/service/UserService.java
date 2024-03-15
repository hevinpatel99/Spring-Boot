package com.roles_privileges.service;

import com.roles_privileges.requestDto.UserRequestDto;
import com.roles_privileges.responseDto.ApiResponseDto;
import com.roles_privileges.responseDto.UserResponseDto;
import org.springframework.http.ResponseEntity;

public interface UserService {
    ResponseEntity<ApiResponseDto> insertUser(UserRequestDto userRequestDto);

    ResponseEntity<ApiResponseDto> getAllUser();

    ResponseEntity<ApiResponseDto> getUserById(Long id);

    ResponseEntity<ApiResponseDto> updateUser(UserRequestDto userDto, Long id);

   ResponseEntity<ApiResponseDto> changeStatus(Long id);

    ResponseEntity<ApiResponseDto> removeUser(Long id);
}
