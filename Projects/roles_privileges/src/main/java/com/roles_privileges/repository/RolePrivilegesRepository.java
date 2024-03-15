package com.roles_privileges.repository;


import com.roles_privileges.mappings.RolePrivilegeMapping;
import com.roles_privileges.model.RoleEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface RolePrivilegesRepository extends JpaRepository<RolePrivilegeMapping, Long> {



    List<RolePrivilegeMapping> findByRoleEntity(RoleEntity roleEntity);

}
