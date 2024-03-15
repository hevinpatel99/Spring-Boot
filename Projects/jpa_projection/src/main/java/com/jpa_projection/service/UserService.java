package com.jpa_projection.service;

import com.jpa_projection.model.UserDepartmentMapping;
import com.jpa_projection.model.UserEntity;
import com.jpa_projection.responseDto.ApiResponseDto;
import org.springframework.http.ResponseEntity;

public interface UserService {
    ResponseEntity<ApiResponseDto> insertUser(UserDepartmentMapping user);

    ResponseEntity<ApiResponseDto> getUser();

    ResponseEntity<ApiResponseDto> getUserById(Long id);

    ResponseEntity<ApiResponseDto> getUserByFirstName(String firstName);

    ResponseEntity<ApiResponseDto> getAllUserByProjection();

    ResponseEntity<ApiResponseDto> getDynamicProjection();

    ResponseEntity<ApiResponseDto> getOpenProjection();
}
