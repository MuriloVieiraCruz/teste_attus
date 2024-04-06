package com.murilovieira.testeattus.dto;

import java.time.LocalDate;
import java.util.Optional;

public record PersonDetailsUpdateDto(

        Optional<String> name,

        Optional<LocalDate> birthDate) {
}
