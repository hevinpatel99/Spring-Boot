package com.organizationServices.requestDto;


import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class RestaurantRequestDto {

    @NotEmpty(message = "Invalid : field is empty, please insert restaurantName first !!")
    @Size(min = 3, max = 30, message = "Invalid : Must be of 3 - 30 characters")
    private String restaurantName;

    @NotEmpty(message = "Invalid : field is empty, please insert restaurantType first !!")
    @Size(min = 3, max = 30, message = "Invalid : Must be of 3 - 30 characters")
    private String restaurantType;
}
