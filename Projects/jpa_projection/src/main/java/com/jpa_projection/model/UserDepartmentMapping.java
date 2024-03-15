package com.jpa_projection.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@RequiredArgsConstructor
public class UserDepartmentMapping {
    private String firstName;
    private String lastName;
    private String userName;
    private String password;
    private Long departmentId;
}
