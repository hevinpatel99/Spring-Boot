package com.roles_privileges.repository;

import com.roles_privileges.mappings.UserRoleMapping;
import com.roles_privileges.model.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRoleRepository extends JpaRepository<UserRoleMapping, Integer> {
    UserRoleMapping findByUser(UserEntity userEntity);

}
