package com.drinkkiluostari.backend.dto;

import java.util.List;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

public record KategoriaDTO(
    Long id,

    @NotEmpty
    @Size(min = 1, max = 100, message = "Nimen pitää olla 1-100 merkkiä pitkä")
    String nimi,
    
    List<Long> tuoteIdt) {

}
