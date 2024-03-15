package com.ratingServices.controller;

import com.ratingServices.requestDto.RatingRequestDto;
import com.ratingServices.responseDto.ApiResponseDto;
import com.ratingServices.service.RatingService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/rating")
@RequiredArgsConstructor
@CrossOrigin(origins = "*",allowedHeaders = "*")
public class RatingController {


    private final RatingService ratingService;


    @PostMapping("/insertRating")
    public ResponseEntity<ApiResponseDto> insertRating(@RequestBody @Valid RatingRequestDto ratingRequestDto) {
        return ratingService.insertRating(ratingRequestDto);
    }

    @GetMapping("/getAllRating")
    public ResponseEntity<ApiResponseDto> getAllRating() {
        return ratingService.getAllRating();
    }


//    @PreAuthorize("hasAnyAuthority('SCOPE_internal')")
    @GetMapping("/getRatingByUserId/{userId}")
    public ResponseEntity<ApiResponseDto> getRatingByUserId(@PathVariable Long userId) {
        return ratingService.getRatingByUserId(userId);
    }

//    @PreAuthorize("hasAnyAuthority('SCOPE_internal')")

    @GetMapping("/getRatingByRestaurantId/{restaurantId}")
    public ResponseEntity<ApiResponseDto> getRatingByRestaurantId(@PathVariable Long restaurantId) {
        return ratingService.getRatingByRestaurantId(restaurantId);
    }

//    @PreAuthorize("hasAnyAuthority('SCOPE_internal')")
    @GetMapping("/getRatingById/{id}")
    public ResponseEntity<ApiResponseDto> getRatingById(@PathVariable Long id) {
        return ratingService.getRatingById(id);
    }


}