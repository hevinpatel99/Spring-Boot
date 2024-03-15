package com.userrole.repository;

import com.userrole.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;


/**
 *
 * @author Hevin Mulani
 * repository interface for user entity.
 */
@Repository
public interface UserRepository extends JpaRepository<UserEntity, Long> {


    @Query("from UserEntity where  userName = ?1")
    Optional<UserEntity> findByUserName(String username);

}
