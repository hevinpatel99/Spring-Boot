package com.ratingServices.entity;


import javax.persistence.*;
import lombok.*;

import java.io.Serializable;

@Getter
@Setter
@Entity
@Builder
@Table(name = "rating")
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class RatingEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long ratingId;
    private Long userId;
    private Long restaurantId;
    private float rating;
    private String feedBack;

    @Transient
    private RestaurantEntity restaurantEntity;
}
