package com.murilovieira.testeattus.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;

public record PersonDetailsCreateDto(

        @NotBlank
        String name,

        @NotNull
        LocalDate birthDate) {
}
