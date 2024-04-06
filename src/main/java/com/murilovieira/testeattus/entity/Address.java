package com.murilovieira.testeattus.entity;

import com.murilovieira.testeattus.entity.enums.AddressType;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
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
    private String street;

    @NotBlank(message = "Cep is required")
    @Column(name = "ds_cep", nullable = false)
    private String cep;

    @NotBlank(message = "Number is required")
    @Column(name = "nr_number", nullable = false)
    private String number;

    @NotBlank(message = "City is required")
    @Column(name = "ds_city", nullable = false)
    private String city;

    @NotBlank(message = "State is required")
    @Column(name = "ds_state", nullable = false)
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