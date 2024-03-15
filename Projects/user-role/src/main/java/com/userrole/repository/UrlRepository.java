package com.userrole.repository;


import com.userrole.entity.UrlEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.persistence.NamedNativeQuery;
import java.util.Optional;
/**
 * @author Hevin Mulani
 * repository interface for url entity.
 */
@Repository
public interface UrlRepository extends JpaRepository<UrlEntity, Long> {
    @Query("FROM UrlEntity where urlName = :requestURI")
    Optional<UrlEntity> findByUrlName(String requestURI);
}
