package com.jpa_audit.model;

import lombok.*;

import javax.persistence.Entity;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Login {

    private String userName;

    private String password;
}
