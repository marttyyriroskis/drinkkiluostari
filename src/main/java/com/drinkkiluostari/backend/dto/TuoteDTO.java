package com.drinkkiluostari.backend.dto;

import java.util.List;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;

public record TuoteDTO(
    @NotEmpty
    @Size(min = 1, max = 100, message = "Nimen pitää olla 1-100 merkkiä pitkä")
    String nimi,

    @NotEmpty
    @PositiveOrZero(message = "Hinnan pitää olla 0 tai enemmän")
    double hinta,

    KategoriaDTO kategoria,

    List<Long> tilausriviIdt) {

}
