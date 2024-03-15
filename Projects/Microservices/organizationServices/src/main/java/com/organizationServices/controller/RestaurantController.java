package com.organizationServices.controller;

import com.organizationServices.requestDto.RestaurantRequestDto;
import com.organizationServices.responseDto.ApiResponseDto;
import com.organizationServices.service.RestaurantService;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/restaurant")
@RequiredArgsConstructor
public class RestaurantController {


    private final RestaurantService restaurantService;


    @PostMapping("/insertRestaurant")
    public ResponseEntity<ApiResponseDto> insertRestaurant(@RequestBody @Valid RestaurantRequestDto restaurantRequestDto) {
        return restaurantService.insertRestaurant(restaurantRequestDto);
    }

    @GetMapping("/getAllRestaurant")
    public ResponseEntity<ApiResponseDto> getAllRestaurant() {
        return restaurantService.getAllRestaurant();
    }


    @PreAuthorize("hasAnyAuthority('SCOPE_internal')")
    @GetMapping("/getRestaurantById/{id}")
    public ResponseEntity<ApiResponseDto> getRestaurantById(@PathVariable Long id) {
        return restaurantService.getRestaurantById(id);
    }


}