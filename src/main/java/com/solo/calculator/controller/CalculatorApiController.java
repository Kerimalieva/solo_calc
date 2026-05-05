package com.solo.calculator.controller;

import com.solo.calculator.dto.CalculateRequest;
import com.solo.calculator.service.CalculatorService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/calc")
@RequiredArgsConstructor
public class CalculatorApiController {

    private final CalculatorService calculatorService;

    @PostMapping("/price")
    public ResponseEntity<?> calculatePrice(@RequestBody CalculateRequest request) {
        try {
            double price = calculatorService.calculatePrice(request.getServiceId(), request.getValue());
            return ResponseEntity.ok(price);
        } catch (RuntimeException e) {
            if (e.getMessage().contains("below minimum")) {
                return ResponseEntity.badRequest().body("Value below minimum");
            } else if (e.getMessage().contains("above maximum")) {
                return ResponseEntity.badRequest().body("Value above maximum");
            } else if (e.getMessage().contains("Service not found")) {
                return ResponseEntity.notFound().build();
            } else {
                return ResponseEntity.internalServerError().body(e.getMessage());
            }
        }
    }
}