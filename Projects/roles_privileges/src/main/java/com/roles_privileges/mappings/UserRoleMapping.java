package com.roles_privileges.mappings;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.roles_privileges.model.RoleEntity;
import com.roles_privileges.model.UserEntity;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "User_Role_Mapping")
public class UserRoleMapping {

    @Id
    @GeneratedValue(
            strategy = GenerationType.IDENTITY
    )
    private Long id;
    @JsonIgnore
    @ManyToOne(
            fetch = FetchType.LAZY
//            cascade = CascadeType.ALL

    )
    @JoinColumn(
            name = "user_id",
            referencedColumnName = "id"
    )
    private UserEntity user;

    @JsonIgnore
    @ManyToOne(
            fetch = FetchType.LAZY
//            cascade = {CascadeType.ALL}
    )
    @JoinColumn(
            name = "role_id",
            referencedColumnName = "id"
    )
    private RoleEntity role;


}
