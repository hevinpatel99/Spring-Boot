package com.userservices.controller;


import com.userservices.entity.UserEntity;
import com.userservices.requestDto.UserRequestDto;
import com.userservices.responseDto.ApiResponseDto;
import com.userservices.service.UserService;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;


@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
@CrossOrigin(origins = "*",allowedHeaders = "*")
public class UserController {


    Logger log = LoggerFactory.getLogger(UserController.class);

    private final UserService userService;


    @PostMapping("/insertUser")
    public ResponseEntity<ApiResponseDto> insertUser(@RequestBody @Valid UserRequestDto userRequestDto) {
        return userService.insertUser(userRequestDto);
    }

    @GetMapping("/getAllUser")
    @CircuitBreaker(name = "ratingRestaurantBreaker", fallbackMethod = "allRatingRestaurantFallback")
    public ResponseEntity<ApiResponseDto> getAllUser() {
        return userService.getAllUser();
    }

//    int retryCount = 1;

    @GetMapping("/getUser/{id}")
    @CircuitBreaker(name = "ratingRestaurantBreaker", fallbackMethod = "ratingRestaurantFallback")
   /* @Retry(name = "ratingRestaurantBreaker", fallbackMethod = "ratingRestaurantFallback")
    @RateLimiter(name = "userRateLimiter", fallbackMethod = "ratingRestaurantFallback")*/
    public ResponseEntity<ApiResponseDto> getUserById(@PathVariable Long id) {
//        retryCount++;
//        log.info(String.valueOf(retryCount));
        return userService.getUserById(id);
    }

    /**
     * @param
     * @return
     */
    public ResponseEntity<ApiResponseDto> allRatingRestaurantFallback(Exception ex) {
        UserEntity dummyUser = UserEntity.builder().name("dummyUser").userName("dummy@gmail.com").build();
        return ResponseEntity.ok(new ApiResponseDto(HttpStatus.INTERNAL_SERVER_ERROR, "Fallback is executed because service is down : " + ex.getMessage(), dummyUser));
    }


    public ResponseEntity<ApiResponseDto> ratingRestaurantFallback(Long id, Exception ex) {
        UserEntity dummyUser = UserEntity.builder().name("dummyUser").userName("dummy@gmail.com").build();
        return ResponseEntity.ok(new ApiResponseDto(HttpStatus.INTERNAL_SERVER_ERROR, "Fallback is executed because service is down : " + ex.getMessage(), dummyUser));
    }
}
