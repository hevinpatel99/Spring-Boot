package com.userrole.responseDto;


import com.userrole.entity.RoleEntity;
import com.userrole.entity.UserEntity;
import lombok.*;



/**
 * @author Hevin Mulani
 * response dto class for user-role responses.
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class UserRoleResponseDto {

    private UserEntity user;
    private RoleEntity role;
}
