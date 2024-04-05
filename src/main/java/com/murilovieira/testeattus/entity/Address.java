package com.murilovieira.testeattus.entity;

import com.murilovieira.testeattus.entity.enums.AddressType;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
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

    @NotBlank
    @Column(name = "ds_logradouro", nullable = false)
    private String logradouro;

    @NotBlank
    @Column(name = "ds_cep", nullable = false)
    private String cep;

    @NotBlank
    @Column(name = "nr_number", nullable = false)
    private String number;

    @NotBlank
    @Column(name = "ds_city", nullable = false)
    private String city;

    @NotBlank
    @Column(name = "ds_state", nullable = false)
    private String state;

    @Enumerated(value = EnumType.STRING)
    @Column(name = "ds_address_type", nullable = false)
    private AddressType addressType;
}
