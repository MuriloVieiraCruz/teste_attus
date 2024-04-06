package com.murilovieira.testeattus.dto;

import com.murilovieira.testeattus.entity.Person;
import com.murilovieira.testeattus.entity.enums.AddressType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record AddressDetailsCreateDto(

        @NotBlank(message = "Street is required")
        String street,

        @NotBlank(message = "Cep is required")
        String cep,

        @NotBlank(message = "Number is required")
        String number,

        @NotBlank(message = "City is required")
        String city,

        @NotBlank(message = "State is required")
        String state,

        @NotNull(message = "Address type is required")
        AddressType addressType,

        @NotNull(message = "Person id is required")
        Long personId) {
}