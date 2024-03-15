package com.jpa_projection.projection;

import com.jpa_projection.model.Department;
import com.jpa_projection.model.UserEntity;
import org.springframework.beans.factory.annotation.Value;

public interface UserProjection_close {

    String getFirstName();

    String getLastName();

    Department getDepartment();

}
