package com.userrole.requestDto;

import lombok.*;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.util.Set;

/**
 *
 * @author Hevin Mulani
 * DTO class representing Role request containing roleName.
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class RoleRequestDto {


    @NotEmpty(message = "Invalid : field is empty, please insert role name first !!")
    private String roleName;
}
