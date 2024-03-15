package com.roles_privileges.requestDto;

import com.roles_privileges.model.PrivilegeEntity;
import com.roles_privileges.model.RoleEntity;
import lombok.*;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class RolePrivilegeRequestDto {

    private RoleEntity role;
    private PrivilegeEntity privilege;
}
