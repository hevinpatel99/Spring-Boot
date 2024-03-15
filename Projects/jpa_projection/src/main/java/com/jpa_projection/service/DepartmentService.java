package com.jpa_projection.service;

import com.jpa_projection.model.Department;
import com.jpa_projection.responseDto.ApiResponseDto;
import org.springframework.http.ResponseEntity;

public interface DepartmentService {
    ResponseEntity<ApiResponseDto> insertDepartment(Department department);
}
