package com.userservices.entity;


import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class RestaurantEntity {
    private Long id;
    private String restaurantName;
    private String restaurantType;
    private boolean status;
}
