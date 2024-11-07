package com.drinkkiluostari.backend.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;

public record CreateTuoteDTO(
    @NotEmpty
    @Size(min = 1, max = 100, message = "Nimen pitää olla 1-100 merkkiä pitkä")
    String nimi,

    @NotNull
    @PositiveOrZero(message = "Hinnan pitää olla 0 tai enemmän")
    double hinta,

    KategoriaDTO kategoria) {

}
