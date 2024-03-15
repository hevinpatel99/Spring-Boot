package com.userrole.repository;

import com.userrole.entity.RoleEntity;
import com.userrole.entity.UrlEntity;
import com.userrole.mappings.UrlRoleMapping;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.persistence.criteria.From;
import java.util.List;

/**
 * @author Hevin Mulani
 * repository interface for UrlRoleMapping entity.
 */
@Repository
public interface UrlRoleRepository extends JpaRepository<UrlRoleMapping, Long> {


    @Query("FROM UrlRoleMapping where url = :urlEntity")
    List<UrlRoleMapping> findByUrl(UrlEntity urlEntity);


    @Query("FROM UrlRoleMapping where role = :roleEntity")
    List<UrlRoleMapping> findByRole(RoleEntity roleEntity);
}
