package com.solo.calculator.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "service")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Service {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String name;

    @Column(length = 2000)
    private String description;

    private String imageUrl;

    private boolean active;

    @OneToOne(mappedBy = "service", cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
    private CalculatorConfig calculatorConfig;
}