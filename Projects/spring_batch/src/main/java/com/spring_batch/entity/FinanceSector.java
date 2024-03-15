package com.spring_batch.entity;


import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Entity
@Builder
@Table(name = "finance")
public class FinanceSector {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    private Long id;

    @Column(name = "Storage",nullable = false)
    private String storage;
    @Column(name = "Organization",nullable = false)
    private String organization ;

}
