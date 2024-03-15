package com.userrole.entity;

import lombok.*;

import javax.persistence.*;

/**
 *
 * @author Hevin Mulani
 * Entity class representing Url info.
 * Contains information about id,urlName.
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Entity
@Builder
@Table(name = "url")
public class UrlEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;
    private String urlName;
}
