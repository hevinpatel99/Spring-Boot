package com.organizationServices.responseDto;


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
public class RestaurantResponseDto {

    private long id;
    private String restaurantName;
    private String restaurantType;
    private boolean status;

}
