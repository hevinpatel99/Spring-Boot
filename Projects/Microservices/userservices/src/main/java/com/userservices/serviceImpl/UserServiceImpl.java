package com.userservices.serviceImpl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.userservices.entity.RatingEntity;
import com.userservices.entity.RestaurantEntity;
import com.userservices.entity.UserEntity;
import com.userservices.exception.CustomException;
import com.userservices.feignServices.RatingService;
import com.userservices.feignServices.RestaurantService;
import com.userservices.repository.UserRepository;
import com.userservices.requestDto.UserRequestDto;
import com.userservices.responseDto.ApiResponseDto;
import com.userservices.responseDto.UserResponseDto;
import com.userservices.service.UserService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.io.Reader;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    Logger log = LoggerFactory.getLogger(UserServiceImpl.class);

    private final UserRepository userRepository;
    private final ModelMapper modelMapper;
    private final RestTemplate restTemplate;
    private final RestaurantService restaurantService;
    private final RatingService ratingService;

    @Override
    public ResponseEntity<ApiResponseDto> insertUser(UserRequestDto userRequestDto) {
        Optional<UserEntity> byUserName = userRepository.findByUserName(userRequestDto.getUserName());
        if (byUserName.isPresent()) {
            throw new CustomException("user already exists", HttpStatus.BAD_REQUEST);
        }

        //Save User Entity
        UserEntity userEntity = saveUserEntity(userRequestDto);
        this.userRepository.save(userEntity);

        UserResponseDto userResponseDto = modelMapper.map(userEntity, UserResponseDto.class);
        return ResponseEntity.ok(new ApiResponseDto(HttpStatus.CREATED, "User Inserted Successfully", userResponseDto));
    }

    @Override
    public ResponseEntity<ApiResponseDto> getAllUser() {
        List<UserEntity> allUser = userRepository.findAll();
        log.info("All USER : " + allUser);
        if (!allUser.isEmpty()) {
            System.out.println("UserResponseDtoList : " + allUser);
            allUser.forEach(user -> {
                ApiResponseDto responseData = ratingService.getRatingByUserId(user.getId()).getBody();
                //                ApiResponseDto responseData = restTemplate.getForObject("http://RATING-SERVICE/rating/getRatingByUserId/" + user.getId(), ApiResponseDto.class);
                if (responseData != null && responseData.getData() != null) {
                    System.out.println("Rating Entity : " + responseData.getData());
                    List<RatingEntity> ratingEntityList = modelMapper.map(responseData.getData(), new TypeToken<List<RatingEntity>>() {
                    }.getType());
                    List<RatingEntity> ratingEntities = ratingEntityList.stream().peek(rating -> {
                        ApiResponseDto getRestaurantDetails = restaurantService.getRestaurantById(rating.getRestaurantId()).getBody();
//                        ApiResponseDto getRestaurantDetails = restTemplate.getForObject("http://ORGANIZATION-SERVICE/restaurant/getRestaurantById/" + rating.getRestaurantId(), ApiResponseDto.class);
                        if (getRestaurantDetails != null && getRestaurantDetails.getData() != null) {
                            RestaurantEntity restaurantEntity = modelMapper.map(getRestaurantDetails.getData(), new TypeToken<RestaurantEntity>() {
                            }.getType());
                            rating.setRestaurantEntity(restaurantEntity);
                        }
                    }).toList();
                    user.setRatings(ratingEntities);
                }
            });
            List<UserResponseDto> userResponseDtoList = allUser.stream().map(userEntity -> modelMapper.map(userEntity, UserResponseDto.class)).collect(Collectors.toList());
            return ResponseEntity.ok(new ApiResponseDto(HttpStatus.CREATED, "User Inserted Successfully", userResponseDtoList));
        }
        return ResponseEntity.ok(new ApiResponseDto(HttpStatus.NOT_FOUND, "Users not found", Collections.EMPTY_LIST));
    }


    @Override
    public ResponseEntity<ApiResponseDto> getUserById(Long id) {
        Optional<UserEntity> userEntity = userRepository.findById(id);
        if (userEntity.isPresent()) {
            UserEntity userEntity1 = userEntity.get();
            UserResponseDto userResponseDto = modelMapper.map(userEntity1, UserResponseDto.class);
            ApiResponseDto responseData = restTemplate.getForObject("http://RATING-SERVICE/rating/getRatingByUserId/" + userEntity1.getId(), ApiResponseDto.class);
            if (responseData != null && responseData.getData() != null) {
                System.out.println("Rating Entity : " + responseData.getData());
                List<RatingEntity> ratingEntityList = modelMapper.map(responseData.getData(), new TypeToken<List<RatingEntity>>() {
                }.getType());
                System.out.println(ratingEntityList);
                List<RatingEntity> ratingEntities = ratingEntityList.stream().peek(rating -> {
                    ApiResponseDto getRestaurantDetails = restTemplate.getForObject("http://ORGANIZATION-SERVICE/restaurant/getRestaurantById/" + rating.getRestaurantId(), ApiResponseDto.class);
                    if (getRestaurantDetails != null && getRestaurantDetails.getData() != null) {
                        RestaurantEntity restaurantEntity = modelMapper.map(getRestaurantDetails.getData(), new TypeToken<RestaurantEntity>() {
                        }.getType());
                        rating.setRestaurantEntity(restaurantEntity);
                    }
                }).toList();
                userResponseDto.setRatings(ratingEntities);
            }
            return ResponseEntity.ok(new ApiResponseDto(HttpStatus.OK, "User Get Successfully", userResponseDto));
        } else {
            return ResponseEntity.ok(new ApiResponseDto(HttpStatus.NOT_FOUND, "Users not found", Collections.EMPTY_LIST));
        }
    }

    public UserEntity saveUserEntity(UserRequestDto userRequestDto) {
        return UserEntity.builder().name(userRequestDto.getName())
                .userName(userRequestDto.getUserName())
                .password(userRequestDto.getPassword())
                .status(Boolean.TRUE)
                .build();
    }
}
