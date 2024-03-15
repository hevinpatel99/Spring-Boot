package com.jpa_audit.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.jpa_audit.model.Role;
import lombok.*;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;

import javax.persistence.Entity;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Data
public class UserDto {

    @NotEmpty(message = "Invalid : First Name is empty")
    @Size(min = 3, max = 30, message = "Invalid : Must be of 3 - 30 characters")
    private String firstName;

    @NotEmpty(message = "Invalid : Last Name is empty")
    @Size(min = 3, max = 30, message = "Invalid : Must be of 3 - 30 characters")
    private String lastName;

    @NotEmpty(message = "Invalid : EmailId is empty")
    @Email(message = "Email is not valid", regexp = "^[a-zA-Z0-9_!#$%&'*+/=?`{|}~^.-]+@[a-zA-Z0-9.-]+$")
    private String userName;

    @NotEmpty(message = "Invalid : password is empty")
    @Pattern(regexp = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[!@#&()–[{}]:;',?/*~$^+=<>]).{8,20}$", message = "Password must be contain uppercase and lowercase character, at least minimum 8 and maximum 16 character and special character.")
    private String password;
    private String roleName;

    @JsonIgnore
    private Set<Role> roles = new HashSet<>();



}
