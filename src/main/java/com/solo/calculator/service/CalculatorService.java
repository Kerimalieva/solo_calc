package com.solo.calculator.service;

import com.solo.calculator.dto.*;
import com.solo.calculator.model.*;
import com.solo.calculator.repository.*;
import lombok.RequiredArgsConstructor;
import net.objecthunter.exp4j.Expression;
import net.objecthunter.exp4j.ExpressionBuilder;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@org.springframework.stereotype.Service
@RequiredArgsConstructor
public class CalculatorService {

    private final ServiceRepository serviceRepository;
    private final CalculatorConfigRepository configRepository;

    // ---- Управление услугами ----
    public List<ServiceDto> getAllServices() {
        return serviceRepository.findAll().stream()
                .map(this::toServiceDto)
                .collect(Collectors.toList());
    }

    public ServiceDto getService(Long id) {
        Service service = serviceRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Услуга не найдена"));
        return toServiceDto(service);
    }

    @Transactional
    public ServiceDto createService(ServiceDto dto) {
        Service service = Service.builder()
                .name(dto.getName())
                .description(dto.getDescription())
                .imageUrl(dto.getImageUrl())
                .active(dto.isActive())
                .build();
        service = serviceRepository.save(service);
        return toServiceDto(service);
    }

    @Transactional
    public ServiceDto updateService(Long id, ServiceDto dto) {
        Service service = serviceRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Услуга не найдена"));
        service.setName(dto.getName());
        service.setDescription(dto.getDescription());
        service.setImageUrl(dto.getImageUrl());
        service.setActive(dto.isActive());
        service = serviceRepository.save(service);
        return toServiceDto(service);
    }

    @Transactional
    public void deleteService(Long id) {
        serviceRepository.deleteById(id);
    }

    // ---- Управление калькулятором ----
    @Transactional
    public CalculatorConfigDto saveCalculatorConfig(Long serviceId, CalculatorConfigDto dto) {
        Service service = serviceRepository.findById(serviceId)
                .orElseThrow(() -> new RuntimeException("Услуга не найдена"));

        CalculatorConfig config = service.getCalculatorConfig();
        if (config == null) {
            config = CalculatorConfig.builder()
                    .service(service)
                    .build();
        }
        config.setFormula(dto.getFormula());
        config.setActive(dto.isActive());
        config.setParameterLabel(dto.getParameterLabel());
        config.setParameterMin(dto.getParameterMin());
        config.setParameterMax(dto.getParameterMax());
        config.setParameterStep(dto.getParameterStep());
        config.setParameterDefault(dto.getParameterDefault());

        config = configRepository.save(config);
        service.setCalculatorConfig(config);
        serviceRepository.save(service);

        return getCalculatorConfig(serviceId);
    }

    public CalculatorConfigDto getCalculatorConfig(Long serviceId) {
        Service service = serviceRepository.findById(serviceId)
                .orElseThrow(() -> new RuntimeException("Услуга не найдена"));
        CalculatorConfig config = service.getCalculatorConfig();
        if (config == null) return null;
        return CalculatorConfigDto.builder()
                .id(config.getId())
                .formula(config.getFormula())
                .active(config.isActive())
                .parameterLabel(config.getParameterLabel())
                .parameterMin(config.getParameterMin())
                .parameterMax(config.getParameterMax())
                .parameterStep(config.getParameterStep())
                .parameterDefault(config.getParameterDefault())
                .build();
    }

    // ---- Расчёт цены ----
    public double calculatePrice(Long serviceId, Double x) {
        Service service = serviceRepository.findById(serviceId)
                .orElseThrow(() -> new RuntimeException("Service not found"));
        CalculatorConfig config = service.getCalculatorConfig();
        if (config == null || !config.isActive()) {
            throw new RuntimeException("Calculator not active");
        }
        if (x == null) {
            x = config.getParameterDefault() != null ? config.getParameterDefault() : 0.0;
        }
        // Проверка min/max
        if (config.getParameterMin() != null && x < config.getParameterMin())
            throw new RuntimeException("Value below minimum");
        if (config.getParameterMax() != null && x > config.getParameterMax())
            throw new RuntimeException("Value above maximum");

        Expression expression = new ExpressionBuilder(config.getFormula())
                .variables("x")
                .build();
        expression.setVariable("x", x);
        double result = expression.evaluate();
        return Math.round(result * 100.0) / 100.0;
    }

    // ---- Мапперы ----
    private ServiceDto toServiceDto(Service service) {
        ServiceDto dto = ServiceDto.builder()
                .id(service.getId())
                .name(service.getName())
                .description(service.getDescription())
                .imageUrl(service.getImageUrl())
                .active(service.isActive())
                .build();
        if (service.getCalculatorConfig() != null) {
            CalculatorConfig config = service.getCalculatorConfig();
            dto.setCalculatorConfig(CalculatorConfigDto.builder()
                    .id(config.getId())
                    .formula(config.getFormula())
                    .active(config.isActive())
                    .parameterLabel(config.getParameterLabel())
                    .parameterMin(config.getParameterMin())
                    .parameterMax(config.getParameterMax())
                    .parameterStep(config.getParameterStep())
                    .parameterDefault(config.getParameterDefault())
                    .build());
        }
        return dto;
    }
}