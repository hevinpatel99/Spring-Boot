package com.jpa_projection.dto;

import com.jpa_projection.model.Department;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@RequiredArgsConstructor
public class UserEntityViewDto {
    private String firstName;
    private String lastName;
//    private Department department;
    private String departmentName;

}
