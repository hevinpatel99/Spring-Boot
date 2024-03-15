package com.userrole.repository;


import com.userrole.entity.UserEntity;
import com.userrole.mappings.UserRoleMapping;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author Hevin Mulani
 * repository interface for UserRoleMapping entity.
 */
@Repository
public interface UserRoleRepository extends JpaRepository<UserRoleMapping, Long> {


    @Query("from UserRoleMapping where user = ?1")
    List<UserRoleMapping> findByUser(UserEntity userEntity);


}
