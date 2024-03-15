package com.jpa_projection.projection;

import org.springframework.beans.factory.annotation.Value;

public interface UserProjection_open {

    @Value("#{target.firstName + ' ' + target.lastName }")
    String getFullName();

    @Value("#{target.department.departmentName}")
    String getDepartment();

    @Value("#{target.firstName}")
    String getFirstName();
}
