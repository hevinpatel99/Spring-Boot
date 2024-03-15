package com.spring_batch.repository;

import com.spring_batch.entity.FinanceSector;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FinanceSectorRepository extends JpaRepository<FinanceSector, Long> {
}
