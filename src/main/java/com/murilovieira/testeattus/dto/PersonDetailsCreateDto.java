package com.murilovieira.testeattus.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;

public record PersonDetailsCreateDto(

        @NotBlank(message = "Name is required")
        String name,

        @NotNull(message = "Birth date is required")
        LocalDate birthDate) {
}
