package com.userrole.requestDto;


import com.userrole.entity.RoleEntity;
import com.userrole.entity.UserEntity;
import lombok.*;


/**
 *
 * @author Hevin Mulani
 * DTO class representing user-role request containing user id & role id.
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class UserRoleRequestDto {
    private Long userId;
    private Long roleId;
}
