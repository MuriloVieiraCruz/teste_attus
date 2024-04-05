package com.murilovieira.testeattus.dto;

import com.murilovieira.testeattus.entity.Person;
import com.murilovieira.testeattus.entity.enums.AddressType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record AddressDetailsCreateDto(

        @NotBlank
        String street,

        @NotBlank
        String cep,

        @NotBlank
        String number,

        @NotBlank
        String city,

        @NotBlank
        String state,

        @NotNull
        AddressType addressType,

        @NotNull
        Long personId) {
}