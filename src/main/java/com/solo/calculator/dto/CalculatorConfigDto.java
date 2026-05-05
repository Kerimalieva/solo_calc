package com.solo.calculator.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CalculatorConfigDto {
    private Long id;
    private boolean active;
    private String formula;
    private String parameterLabel;
    private Double parameterMin;
    private Double parameterMax;
    private Double parameterStep;
    private Double parameterDefault;
}