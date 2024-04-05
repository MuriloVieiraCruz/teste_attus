package com.murilovieira.testeattus.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;
import java.util.Optional;

public record PersonDetailsUpdateDto(

        Optional<String> name,

        Optional<LocalDate> birthDate) {
}
