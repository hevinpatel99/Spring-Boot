package com.roles_privileges.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;

import java.util.List;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class TokenClaimsDto {

    private Long userId;
    private String userName;
    private String password;
    private List<GrantedAuthority> grantedAuthorities;
}
