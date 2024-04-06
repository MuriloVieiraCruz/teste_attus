package com.murilovieira.testeattus.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@SequenceGenerator(name = "person_sequence", sequenceName = "sq_tb_person", allocationSize = 1)
@Table(name = "tb_person")
public class Person {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "person_sequence")
    private Long id;

    @NotBlank(message = "Name is required")
    @Column(name = "nm_name", nullable = false)
    @Size(min = 3, max = 100, message = "Name must be between 3 and 60 characters")
    private String name;

    @NotNull(message = "Birth date is required")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy", timezone = "GMT")
    @Column(name = "dt_birth_date", nullable = false)
    private LocalDate birthDate;
}
