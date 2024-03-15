package com.jpa_projection.controller;

import com.jpa_projection.model.Department;
import com.jpa_projection.model.UserEntity;
import com.jpa_projection.responseDto.ApiResponseDto;
import com.jpa_projection.service.DepartmentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/department")
@RequiredArgsConstructor
public class DepartmentController {


    private final DepartmentService departmentService;

    @PostMapping("/insertDepartment")
    public ResponseEntity<ApiResponseDto> insertDepartment(@RequestBody Department department) {
        return departmentService.insertDepartment(department);
    }
}
