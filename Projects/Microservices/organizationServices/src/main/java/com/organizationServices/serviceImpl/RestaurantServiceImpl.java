package com.organizationServices.serviceImpl;

import com.organizationServices.entity.RestaurantEntity;
import com.organizationServices.exception.CustomException;
import com.organizationServices.repository.RestaurantRepository;
import com.organizationServices.requestDto.RestaurantRequestDto;
import com.organizationServices.responseDto.ApiResponseDto;
import com.organizationServices.responseDto.RestaurantResponseDto;
import com.organizationServices.service.RestaurantService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class RestaurantServiceImpl implements RestaurantService {

    private final RestaurantRepository restaurantRepository;
    private final ModelMapper modelMapper;

    @Override
    public ResponseEntity<ApiResponseDto> insertRestaurant(RestaurantRequestDto restaurantRequestDto) {
        Optional<RestaurantEntity> byRestaurantName = restaurantRepository.findByRestaurantName(restaurantRequestDto.getRestaurantName());
        if (byRestaurantName.isPresent()) {
            throw new CustomException("Restaurant already exists", HttpStatus.BAD_REQUEST);
        }

        //Save User Entity
        RestaurantEntity restaurantEntity = saveRestaurantEntity(restaurantRequestDto);
        this.restaurantRepository.save(restaurantEntity);

        RestaurantResponseDto restaurantResponseDto = modelMapper.map(restaurantEntity, RestaurantResponseDto.class);
        return ResponseEntity.ok(new ApiResponseDto(HttpStatus.CREATED, "Restaurant Inserted Successfully", restaurantResponseDto));
    }

    @Override
    public ResponseEntity<ApiResponseDto> getAllRestaurant() {
        List<RestaurantEntity> allRestaurant = restaurantRepository.findAll();
        System.out.println("All Restaurant : " + allRestaurant);
        if (!allRestaurant.isEmpty()) {
            List<RestaurantResponseDto> restaurantResponseDtoList = allRestaurant.stream().map(restaurantEntity -> modelMapper.map(restaurantEntity, RestaurantResponseDto.class)).collect(Collectors.toList());
            return ResponseEntity.ok(new ApiResponseDto(HttpStatus.OK, "All Restaurant Get Successfully", restaurantResponseDtoList));
        } else {
            return ResponseEntity.ok(new ApiResponseDto(HttpStatus.NOT_FOUND, "Restaurant not found", Collections.EMPTY_LIST));
        }
    }

    @Override
    public ResponseEntity<ApiResponseDto> getRestaurantById(Long id) {
        Optional<RestaurantEntity> restaurantEntity = restaurantRepository.findById(id);
        if (restaurantEntity.isPresent()) {
            RestaurantResponseDto restaurantResponseDto = modelMapper.map(restaurantEntity.get(), RestaurantResponseDto.class);
            return ResponseEntity.ok(new ApiResponseDto(HttpStatus.OK, "Restaurant Get Successfully", restaurantResponseDto));
        } else {
            return ResponseEntity.ok(new ApiResponseDto(HttpStatus.NOT_FOUND, "Restaurant not found", Collections.EMPTY_LIST));
        }
    }

    public RestaurantEntity saveRestaurantEntity(RestaurantRequestDto restaurantRequestDto) {
        return RestaurantEntity.builder().restaurantName(restaurantRequestDto.getRestaurantName())
                .restaurantType(restaurantRequestDto.getRestaurantType())
                .status(Boolean.TRUE)
                .build();
    }
}
