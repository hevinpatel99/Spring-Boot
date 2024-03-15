package com.ratingServices.responseDto;


import com.ratingServices.entity.RestaurantEntity;
import lombok.*;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class RatingResponseDto {

    private long ratingId;
    private String userId;
    private String restaurantId;
    private float rating;
    private String feedback;
    private RestaurantEntity restaurantEntity;

}
