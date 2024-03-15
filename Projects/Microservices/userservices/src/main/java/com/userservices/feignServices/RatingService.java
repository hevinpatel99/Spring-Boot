package com.userservices.feignServices;


import com.userservices.responseDto.ApiResponseDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient("RATING-SERVICE/rating")
public interface RatingService {

    @GetMapping("/getRatingByUserId/{userId}")
    ResponseEntity<ApiResponseDto> getRatingByUserId(@PathVariable Long userId);

}
