package com.solo.calculator.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ServiceDto {
    private Long id;
    private String name;
    private String description;
    private String imageUrl;
    private boolean active;
    private CalculatorConfigDto calculatorConfig;
}