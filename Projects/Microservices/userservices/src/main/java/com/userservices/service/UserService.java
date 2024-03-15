package com.userservices.service;

import com.userservices.requestDto.UserRequestDto;
import com.userservices.responseDto.ApiResponseDto;
import org.springframework.http.ResponseEntity;

public interface UserService {
    ResponseEntity<ApiResponseDto> insertUser(UserRequestDto userRequestDto);

    ResponseEntity<ApiResponseDto> getAllUser();

    ResponseEntity<ApiResponseDto> getUserById(Long id);
}
