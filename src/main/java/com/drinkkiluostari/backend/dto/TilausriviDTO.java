package com.drinkkiluostari.backend.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;

public record TilausriviDTO(
    @NotNull
    @PositiveOrZero(message = "Hinnan pitää olla 0 tai enemmän")
    double hinta,
    
    @NotNull
    @PositiveOrZero(message = "Alennuksen pitää olla 0 tai enemmän")
    double alennus,
    
    @NotNull
    @PositiveOrZero(message = "Määrän pitää olla 0 tai enemmän")
    int maara,
    
    TuoteDTO tuote,
    
    TilausDTO tilaus) {

}
