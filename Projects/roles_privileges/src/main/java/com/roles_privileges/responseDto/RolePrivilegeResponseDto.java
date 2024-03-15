package com.roles_privileges.responseDto;

import com.roles_privileges.model.PrivilegeEntity;
import com.roles_privileges.model.RoleEntity;
import lombok.*;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class RolePrivilegeResponseDto {

    private RoleEntity role;
    private PrivilegeEntity privilege;
}
