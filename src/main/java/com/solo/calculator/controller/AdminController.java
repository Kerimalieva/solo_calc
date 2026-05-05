package com.solo.calculator.controller;

import com.solo.calculator.dto.CalculatorConfigDto;
import com.solo.calculator.dto.ServiceDto;
import com.solo.calculator.service.CalculatorService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin")
@RequiredArgsConstructor
public class AdminController {

    private final CalculatorService calculatorService;

    @PostMapping("/service/{serviceId}/calculator")
    public CalculatorConfigDto saveCalculator(@PathVariable Long serviceId, @RequestBody CalculatorConfigDto dto) {
        return calculatorService.saveCalculatorConfig(serviceId, dto);
    }
    @GetMapping("/services")
    public List<ServiceDto> getServices() {
        return calculatorService.getAllServices();
    }

    @GetMapping("/service/{id}")
    public ResponseEntity<ServiceDto> getService(@PathVariable Long id) {
        try {
            return ResponseEntity.ok(calculatorService.getService(id));
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("/service")
    public ServiceDto createService(@RequestBody ServiceDto dto) {
        return calculatorService.createService(dto);
    }

    @PutMapping("/service/{id}")
    public ServiceDto updateService(@PathVariable Long id, @RequestBody ServiceDto dto) {
        return calculatorService.updateService(id, dto);
    }

    @DeleteMapping("/service/{id}")
    public ResponseEntity<?> deleteService(@PathVariable Long id) {
        calculatorService.deleteService(id);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/service/{serviceId}/calculator")
    public CalculatorConfigDto getCalculator(@PathVariable Long serviceId) {
        return calculatorService.getCalculatorConfig(serviceId);
    }


}