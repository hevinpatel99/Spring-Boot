package com.organizationServices.service;

import com.organizationServices.requestDto.RestaurantRequestDto;
import com.organizationServices.responseDto.ApiResponseDto;
import org.springframework.http.ResponseEntity;

public interface RestaurantService {
    ResponseEntity<ApiResponseDto> insertRestaurant(RestaurantRequestDto restaurantRequestDto);

    ResponseEntity<ApiResponseDto> getAllRestaurant();

    ResponseEntity<ApiResponseDto> getRestaurantById(Long id);
}
