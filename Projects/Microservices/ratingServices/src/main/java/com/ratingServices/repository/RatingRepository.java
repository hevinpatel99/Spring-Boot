package com.ratingServices.repository;

import com.ratingServices.entity.RatingEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface RatingRepository extends JpaRepository<RatingEntity, Long> {
    List<RatingEntity> findByUserId(Long userId);

    Optional<RatingEntity> findByRestaurantId(Long restaurantId);
}
