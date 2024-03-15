package com.jpa_projection.serviceImpl;

import com.jpa_projection.model.Department;
import com.jpa_projection.model.UserEntity;
import com.jpa_projection.repository.DepartmentRepository;
import com.jpa_projection.responseDto.ApiResponseDto;
import com.jpa_projection.service.DepartmentService;
import lombok.RequiredArgsConstructor;
import org.aspectj.lang.reflect.DeclareParents;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DepartmentServiceImpl implements DepartmentService {

    private final DepartmentRepository departmentRepository;

    @Override
    public ResponseEntity<ApiResponseDto> insertDepartment(Department department) {
        Department department1 = saveDepartment(department);
        departmentRepository.save(department1);
        return ResponseEntity.ok(new ApiResponseDto(HttpStatus.CREATED, "Department inserted successfully", department1));

    }

    private Department saveDepartment(Department department) {
        return Department.builder().departmentName(department.getDepartmentName()).build();
    }
}

