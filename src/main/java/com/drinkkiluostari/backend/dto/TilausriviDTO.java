package com.drinkkiluostari.backend.dto;

public record TilausriviDTO(double hinta, double alennus, int maara, TuoteDTO tuote, TilausDTO tilaus) {

}
