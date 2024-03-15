package com.roles_privileges.responseDto;


import lombok.*;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class PrivilegeResponseDto {

    @NotEmpty(message = "Invalid : field is empty, please insert privilegeName first !!")
    @Size(min = 3, max = 30, message = "Invalid : Must be of 3 - 30 characters")
    private String privilegeName;
}
