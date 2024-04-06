package com.murilovieira.testeattus.entity;

import com.murilovieira.testeattus.entity.enums.AddressType;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@Table(name = "tb_address")
public class Address {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Street is required")
    @Column(name = "ds_street", nullable = false)
    @Size(min = 3, max = 200, message = "Street must be between 3 and 200 characters")
    private String street;

    @NotBlank(message = "Cep is required")
    @Column(name = "ds_cep", nullable = false)
    @Size(min = 8, max = 8, message = "Cep must be 8 characters")
    private String cep;

    @NotBlank(message = "Number is required")
    @Column(name = "nr_number", nullable = false)
    @Size(min = 1, max = 4, message = "Number must be between 1 and 4 characters")
    private String number;

    @NotBlank(message = "City is required")
    @Column(name = "ds_city", nullable = false)
    @Size(min = 3, max = 100, message = "City must be between 3 and 100 characters")
    private String city;

    @NotBlank(message = "State is required")
    @Column(name = "ds_state", nullable = false)
    @Size(min = 2, max = 2, message = "State must be 2 characters")
    private String state;

    @NotNull(message = "Address type is required")
    @Enumerated(value = EnumType.STRING)
    @Column(name = "ds_address_type", nullable = false)
    private AddressType addressType;

    @NotNull(message = "Person is required")
    @ManyToOne
    @JoinColumn(name = "person_id")
    private Person person;
}