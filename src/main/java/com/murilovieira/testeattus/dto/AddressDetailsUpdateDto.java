package com.murilovieira.testeattus.dto;

import com.murilovieira.testeattus.entity.Person;
import com.murilovieira.testeattus.entity.enums.AddressType;
import jakarta.validation.constraints.NotNull;
import java.util.Optional;

public record AddressDetailsUpdateDto(

        Optional<String> street,

        Optional<String> cep,

        Optional<String> number,

        Optional<String> city,

        Optional<String> state,

        Optional<AddressType> addressType,

        @NotNull
        Long personId) {
}