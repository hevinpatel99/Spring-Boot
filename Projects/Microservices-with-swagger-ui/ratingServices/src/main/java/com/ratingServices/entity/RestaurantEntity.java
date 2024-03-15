package com.ratingServices.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RestaurantEntity implements Serializable {
    private Long id;
    private String restaurantName;
    private String restaurantType;
    private boolean status;
}
