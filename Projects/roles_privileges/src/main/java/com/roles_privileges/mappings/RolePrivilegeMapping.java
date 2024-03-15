package com.roles_privileges.mappings;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.roles_privileges.model.PrivilegeEntity;
import com.roles_privileges.model.RoleEntity;
import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
@Table(name = "Role_Privilege_Mapping")
public class RolePrivilegeMapping {

    @Id
    @GeneratedValue(
            strategy = GenerationType.IDENTITY
    )
    private Long id;
    @JsonIgnore
    @ManyToOne(
            fetch = FetchType.EAGER,
            cascade = {CascadeType.ALL}
    )
    @JoinColumn(
            name = "role_id",
            referencedColumnName = "id"
    )
    private RoleEntity roleEntity;

    @JsonIgnore
    @ManyToOne(
            fetch = FetchType.EAGER,
            cascade = {CascadeType.ALL}
    )
    @JoinColumn(
            name = "privilege_id",
            referencedColumnName = "id"
    )
    private PrivilegeEntity privilegeEntity;
}
