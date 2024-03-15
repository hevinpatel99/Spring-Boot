package com.ratingServices.serviceImpl;


import com.ratingServices.entity.RatingEntity;
import com.ratingServices.entity.RestaurantEntity;
import com.ratingServices.feignServices.RestaurantService;
import com.ratingServices.repository.RatingRepository;
import com.ratingServices.requestDto.RatingRequestDto;
import com.ratingServices.responseDto.ApiResponseDto;
import com.ratingServices.responseDto.RatingResponseDto;
import com.ratingServices.service.RatingService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class RatingServiceImpl implements RatingService {

    private final RatingRepository ratingRepository;
    private final ModelMapper modelMapper;
    private final RestaurantService restaurantService;

    @Override
    public ResponseEntity<ApiResponseDto> insertRating(RatingRequestDto ratingRequestDto) {
        RatingEntity ratingEntity = saveRatingEntity(ratingRequestDto);
        this.ratingRepository.save(ratingEntity);

        RatingResponseDto restaurantResponseDto = modelMapper.map(ratingEntity, RatingResponseDto.class);
        return ResponseEntity.ok(new ApiResponseDto(HttpStatus.CREATED, "Rating Inserted Successfully", restaurantResponseDto));
    }

    @Override
    public ResponseEntity<ApiResponseDto> getRatingById(Long id) {
        Optional<RatingEntity> ratingEntity = ratingRepository.findById(id);
        if (ratingEntity.isPresent()) {
            RatingEntity getRatingEntity = ratingEntity.get();
            ApiResponseDto getRestaurantDetails = restaurantService.getRestaurantById(getRatingEntity.getRestaurantId()).getBody();
            if (getRestaurantDetails != null && getRestaurantDetails.getData() != null) {
                RestaurantEntity restaurantEntity = modelMapper.map(getRestaurantDetails.getData(), new TypeToken<RestaurantEntity>() {
                }.getType());
                getRatingEntity.setRestaurantEntity(restaurantEntity);
                RatingResponseDto ratingResponseDto = modelMapper.map(getRatingEntity, RatingResponseDto.class);
                return ResponseEntity.ok(new ApiResponseDto(HttpStatus.OK, "Restaurant Rating Get Successfully", ratingResponseDto));

            }
        }
        return ResponseEntity.ok(new ApiResponseDto(HttpStatus.NOT_FOUND, "Restaurant Rating not found", Collections.EMPTY_LIST));
    }


    private RatingEntity saveRatingEntity(RatingRequestDto ratingRequestDto) {
        return RatingEntity.builder().userId(ratingRequestDto.getUserId()).restaurantId(ratingRequestDto.getRestaurantId()).rating(ratingRequestDto.getRating()).feedBack(ratingRequestDto.getFeedBack()).build();
    }

    @Override
    public ResponseEntity<ApiResponseDto> getAllRating() {
        List<RatingEntity> allRatings = ratingRepository.findAll();
        System.out.println("All Ratings : " + allRatings);
        if (!allRatings.isEmpty()) {
            List<RatingResponseDto> ratingResponseDtoList = allRatings.stream().map(ratingEntity -> modelMapper.map(ratingEntity, RatingResponseDto.class)).collect(Collectors.toList());
            return ResponseEntity.ok(new ApiResponseDto(HttpStatus.OK, "All Rating Get Successfully", ratingResponseDtoList));
        } else {
            return ResponseEntity.ok(new ApiResponseDto(HttpStatus.NOT_FOUND, "Rating not found", Collections.EMPTY_LIST));
        }
    }

    @Override
    public ResponseEntity<ApiResponseDto> getRatingByUserId(Long userId) {
        List<RatingEntity> ratingEntityList = ratingRepository.findByUserId(userId);
        if (!ratingEntityList.isEmpty()) {
            ratingEntityList = ratingEntityList.stream().toList();
            List<RatingResponseDto> ratingResponseDtoList = ratingEntityList.stream().map(ratingEntity -> modelMapper.map(ratingEntity, RatingResponseDto.class)).collect(Collectors.toList());
            return ResponseEntity.ok(new ApiResponseDto(HttpStatus.OK, "User Rating Get Successfully", ratingResponseDtoList));
        } else {
            return ResponseEntity.ok(new ApiResponseDto(HttpStatus.NOT_FOUND, "User Rating not found", Collections.EMPTY_LIST));
        }
    }

    @Override
    public ResponseEntity<ApiResponseDto> getRatingByRestaurantId(Long restaurantId) {
        Optional<RatingEntity> ratingEntity = ratingRepository.findByRestaurantId(restaurantId);
        if (ratingEntity.isPresent()) {
            RatingResponseDto ratingResponseDto = modelMapper.map(ratingEntity.get(), RatingResponseDto.class);
            return ResponseEntity.ok(new ApiResponseDto(HttpStatus.OK, "Restaurant Rating Get Successfully", ratingResponseDto));
        } else {
            return ResponseEntity.ok(new ApiResponseDto(HttpStatus.NOT_FOUND, "Restaurant Rating not found", Collections.EMPTY_LIST));
        }
    }


}