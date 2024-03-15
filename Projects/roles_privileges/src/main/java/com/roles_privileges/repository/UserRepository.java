package com.roles_privileges.repository;

import com.roles_privileges.model.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Long> {


    Optional<UserEntity> findByUserName(String username);
//    UserEntity findByUserName1(String username);

}
