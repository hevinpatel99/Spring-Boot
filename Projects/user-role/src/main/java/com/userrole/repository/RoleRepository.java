package com.userrole.repository;


import com.userrole.entity.RoleEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;


/**
 * @author Hevin Mulani
 * repository interface for role entity.
 */
@Repository
public interface RoleRepository extends JpaRepository<RoleEntity, Long> {
    @Query("from RoleEntity where roleName = ?1")
    Optional<RoleEntity> findByRoleNameIgnoreCase(String roleName);

}
