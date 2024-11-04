package com.drinkkiluostari.backend.dto;

import java.util.List;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

public record PostinumeroDTO(
    Long id,
    
    @NotEmpty(message = "Postinumero ei saa olla tyhjä")
    String postinumero,
    
    @NotEmpty
    @Size(min = 1, max = 100, message = "Postitoimipaikan pitää olla 1-100 merkkiä pitkä")
    String postitoimipaikka,
    
    List<Long> asiakasIdt) {

}
