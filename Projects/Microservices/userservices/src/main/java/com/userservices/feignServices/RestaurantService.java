package com.userservices.feignServices;


import com.userservices.responseDto.ApiResponseDto;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient("ORGANIZATION-SERVICE/restaurant")
public interface RestaurantService {

    @GetMapping("/getRestaurantById/{id}")
     ResponseEntity<ApiResponseDto> getRestaurantById(@PathVariable Long id);
}
