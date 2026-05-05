package com.solo.calculator.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "parameter")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Parameter {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "calculator_config_id")
    private CalculatorConfig calculatorConfig;

    @Column(nullable = false)
    private String name;      // имя переменной в формуле

    @Column(nullable = false)
    private String label;     // отображаемое название

    @Column(nullable = false)
    private String type;

    private boolean required;

    private Integer sortOrder;

    @Column(name = "default_value")
    private String defaultValue;

    private Double minValue;
    private Double maxValue;
    private Double step;

    @OneToMany(mappedBy = "parameter", cascade = CascadeType.ALL, orphanRemoval = true)
    @Builder.Default
    private List<Option> options = new ArrayList<>();
}