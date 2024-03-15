package com.ratingServices.requestDto;


import javax.validation.constraints.*;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class RatingRequestDto {

    @NotNull
    private Long userId;

    @NotNull
    private Long restaurantId;

    @NotNull
    private float rating;

    @NotEmpty(message = "Invalid : field is empty, please insert restaurantType first !!")
    private String feedBack;


}
