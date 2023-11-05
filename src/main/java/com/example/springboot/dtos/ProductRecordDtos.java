package com.example.springboot.dtos;

import java.math.BigDecimal;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record ProductRecordDtos(@NotBlank(message = "O campo 'name' não pode estar em branco") String name, @NotNull(message = "O campo 'value' não pode ser nulo") BigDecimal value) {

}
