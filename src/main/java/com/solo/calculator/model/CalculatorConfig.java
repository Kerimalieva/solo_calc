package com.solo.calculator.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "calculator_config")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CalculatorConfig {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "service_id", nullable = false, unique = true)
    private Service service;

    private boolean active;

    private String formula;

    private String parameterLabel;
    private Double parameterMin;
    private Double parameterMax;
    private Double parameterStep;
    private Double parameterDefault;
}