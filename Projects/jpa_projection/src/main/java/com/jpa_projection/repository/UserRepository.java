package com.jpa_projection.repository;

import com.jpa_projection.dto.UserEntityViewDto;
import com.jpa_projection.model.UserEntity;
import com.jpa_projection.projection.UserProjection_close;
import com.jpa_projection.projection.UserProjection_open;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


/**
 * @author Hevin Mulani
 * repository interface for user entity.
 */
@Repository
public interface UserRepository extends JpaRepository<UserEntity, Long> {

    Optional<UserEntity> findByUserName(String userName);

    @Query("select new com.jpa_projection.dto.UserEntityViewDto(e.firstName, e.lastName, e.department.departmentName) FROM user e")
    List<UserEntityViewDto> findAllUser();

    @Query("select new com.jpa_projection.dto.UserEntityViewDto(e.firstName,e.lastName, e.department.departmentName) from user e where e.id = :id")
    UserEntityViewDto findByUserId(Long id);


    List<UserProjection_close> findAllProjectedBy();

    <T> List<T> findAllProjectedBy(Class<T> type);

    List<UserProjection_open> findAllOpenProjectedBy();

    UserProjection_open findAllOpenProjectedByFirstName(String firstName);
}
