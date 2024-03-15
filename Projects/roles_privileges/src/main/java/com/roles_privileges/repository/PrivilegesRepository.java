package com.roles_privileges.repository;

import com.roles_privileges.model.PrivilegeEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PrivilegesRepository extends JpaRepository<PrivilegeEntity, Long> {
    PrivilegeEntity findByPrivilegeName(String privilege);

}
