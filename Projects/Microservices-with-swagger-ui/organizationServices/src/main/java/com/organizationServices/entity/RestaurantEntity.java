package com.organizationServices.entity;


import javax.persistence.*;
import lombok.*;

@Getter
@Setter
@Entity
@Builder
@Table(name = "restaurant")
@NoArgsConstructor
@AllArgsConstructor
public class RestaurantEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;
    private String restaurantName;
    private String restaurantType;
    private boolean status;
}
