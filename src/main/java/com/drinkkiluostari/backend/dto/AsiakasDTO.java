package com.drinkkiluostari.backend.dto;

import java.util.List;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

public record AsiakasDTO(
    Long id,

    @NotEmpty
    @Size(min = 1, max = 100, message = "Nimen pitää olla 1-100 merkkiä pitkä")
    String nimi,

    @NotEmpty
    @Size(min = 1, max = 100, message = "Katuosoitteen pitää olla 1-100 merkkiä pitkä")
    String katuosoite,
    
    @NotEmpty(message = "Y-tunnus ei saa olla tyhjä")
    String yTunnus,
    
    PostinumeroDTO postinumero,
    List<Long> tilausIdt) {

}
