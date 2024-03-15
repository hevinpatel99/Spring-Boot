package com.userrole.mappings;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.userrole.entity.RoleEntity;
import com.userrole.entity.UrlEntity;
import com.userrole.entity.UserEntity;
import lombok.*;

import javax.persistence.*;


/**
 * @author Hevin Mulani
 * Entity Class for Url-Role mapping.
 */
@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
@Table(name = "Url_Role_Mapping")
public class UrlRoleMapping {

    @Id
    @GeneratedValue(
            strategy = GenerationType.IDENTITY
    )
    private Long id;
    @JsonIgnore
    @ManyToOne(
            fetch = FetchType.EAGER
//            cascade = CascadeType.ALL

    )
    @JoinColumn(
            name = "url_id",
            referencedColumnName = "id"
    )
    @ToString.Exclude
    private UrlEntity url;

    @JsonIgnore
    @ManyToOne(
            fetch = FetchType.EAGER
//            cascade = {CascadeType.ALL}
    )
    @JoinColumn(
            name = "role_id",
            referencedColumnName = "id"
    )
    @ToString.Exclude
    private RoleEntity role;
}
