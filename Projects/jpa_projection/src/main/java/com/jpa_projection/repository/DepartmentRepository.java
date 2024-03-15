package com.jpa_projection.repository;

import com.jpa_projection.model.Department;
import com.jpa_projection.model.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DepartmentRepository extends JpaRepository<Department, Long> {

}
