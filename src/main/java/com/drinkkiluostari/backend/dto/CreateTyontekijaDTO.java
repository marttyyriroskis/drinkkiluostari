package com.drinkkiluostari.backend.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

public record CreateTyontekijaDTO(
    Long id,
    
    @NotEmpty
    @Size(min = 1, max = 50, message = "Etunimen pitää olla 1-50 merkkiä pitkä")
    String etunimi,
    
    @NotEmpty
    @Size(min = 1, max = 50, message = "Sukunimen pitää olla 1-50 merkkiä pitkä")
    String sukunimi,
    
    @NotEmpty(message = "Salasana ei saa olla tyhjä")
    String salasana,
    
    @NotEmpty
    @Size(min = 1, max = 100, message = "Sähköpostin pitää olla 1-100 merkkiä pitkä")
    String sahkoposti,
    
    RooliDTO rooli) {

}
