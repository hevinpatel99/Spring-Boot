package com.roles_privileges.responseDto;

import com.roles_privileges.model.RoleEntity;
import com.roles_privileges.model.UserEntity;
import lombok.*;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class UserRoleResponseDto {

    private UserEntity user;
    private RoleEntity role;
}
