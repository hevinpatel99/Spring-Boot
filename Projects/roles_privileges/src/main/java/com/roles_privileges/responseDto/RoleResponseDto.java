package com.roles_privileges.responseDto;

import lombok.*;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.util.Set;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class RoleResponseDto {


    @NotEmpty(message = "Invalid : field is empty, please insert role name first !!")
    @Size(min = 3, max = 30, message = "Invalid : Must be of 3 - 30 characters")
    private String roleName;

    private Set<String> privilege;
}
