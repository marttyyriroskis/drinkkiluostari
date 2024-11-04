package com.drinkkiluostari.backend.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.PositiveOrZero;

public record TilausriviDTO(
    @NotEmpty
    @PositiveOrZero(message = "Hinnan pitää olla 0 tai enemmän")
    double hinta,
    
    @NotEmpty
    @PositiveOrZero(message = "Alennuksen pitää olla 0 tai enemmän")
    double alennus,
    
    @NotEmpty
    @PositiveOrZero(message = "Määrän pitää olla 0 tai enemmän")
    int maara,
    
    TuoteDTO tuote,
    
    TilausDTO tilaus) {

}
