package com.userservices.requestDto;


import com.userservices.entity.RatingEntity;

import lombok.*;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class UserRequestDto {

    @NotEmpty(message = "Invalid : field is empty, please insert name first !!")
    @Size(min = 3, max = 30, message = "Invalid : Must be of 3 - 30 characters")
    private String name;

    @NotEmpty(message = "Invalid : username is empty")
    @Email(message = "username is not valid", regexp = "^[a-zA-Z0-9_!#$%&'*+/=?`{|}~^.-]+@[a-zA-Z0-9.-]+$")
    private String userName;

    @NotEmpty(message = "Invalid : password is empty")
    @Pattern(regexp = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[!@#&()–[{}]:;',?/*~$^+=<>]).{8,20}$", message = "Password must be contain uppercase and lowercase character, at least minimum 8 and maximum 16 character and special character.")
    private String password;

    private List<RatingEntity> ratings = new ArrayList<>();

}
