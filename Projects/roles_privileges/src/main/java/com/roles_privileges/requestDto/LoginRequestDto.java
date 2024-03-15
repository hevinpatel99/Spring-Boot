package com.roles_privileges.requestDto;

import lombok.*;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Data
public class LoginRequestDto {

    @NotEmpty(message = "Invalid : EmailId is empty")
    @Email(message = "userName is not valid", regexp = "^[a-zA-Z0-9_!#$%&'*+/=?`{|}~^.-]+@[a-zA-Z0-9.-]+$")
    private String userName;

    @NotEmpty(message = "Invalid : password is empty")
    @Pattern(regexp = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[!@#&()–[{}]:;',?/*~$^+=<>]).{8,20}$", message = "Password must be contain uppercase and lowercase character, at least minimum 8 and maximum 16 character and special character.")
    private String password;

}
