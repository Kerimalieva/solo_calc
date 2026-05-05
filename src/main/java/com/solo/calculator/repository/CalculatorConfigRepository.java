package com.solo.calculator.repository;

import com.solo.calculator.model.CalculatorConfig;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CalculatorConfigRepository extends JpaRepository<CalculatorConfig, Long> {
}