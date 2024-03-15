package com.jpa_audit.dto;

import lombok.*;

import javax.validation.constraints.NotEmpty;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Data
public class PermissionDto {

    @NotEmpty(message = "Invalid : PermissionName is empty")
    private String PermissionName;
}
