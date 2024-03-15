package com.ratingServices.service;

import com.ratingServices.requestDto.RatingRequestDto;
import com.ratingServices.responseDto.ApiResponseDto;
import org.springframework.http.ResponseEntity;

public interface RatingService {

    ResponseEntity<ApiResponseDto> getAllRating();

    ResponseEntity<ApiResponseDto> getRatingByUserId(Long userId);

    ResponseEntity<ApiResponseDto> getRatingByRestaurantId(Long restaurantId);

    ResponseEntity<ApiResponseDto> insertRating(RatingRequestDto ratingRequestDto);


    ResponseEntity<ApiResponseDto> getRatingById(Long id);
}
