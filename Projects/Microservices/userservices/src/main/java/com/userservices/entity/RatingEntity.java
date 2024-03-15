package com.userservices.entity;

import lombok.*;

import java.io.Serializable;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class RatingEntity implements Serializable {

    private Long ratingId;
    private Long userId;
    private Long restaurantId;
    private float rating;
    private String feedBack;
    private RestaurantEntity restaurantEntity;
}
