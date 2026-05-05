package com.solo.calculator.dto;

import lombok.Data;

@Data
public class CalculateRequest {
    private Long serviceId;
    private Double value;
}