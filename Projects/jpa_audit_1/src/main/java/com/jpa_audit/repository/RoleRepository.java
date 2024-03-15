package com.jpa_audit.repository;

import com.jpa_audit.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.Optional;

@Repository
public interface RoleRepository extends JpaRepository<Role,Long> {
   @Transactional
    Optional<Role> findByRoleName(String roleName);
}
