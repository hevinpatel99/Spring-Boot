package com.roles_privileges.requestDto;

import com.roles_privileges.model.RoleEntity;
import com.roles_privileges.model.UserEntity;
import lombok.*;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class UserRoleRequestDto {

    private UserEntity user;
    private RoleEntity role;
}
