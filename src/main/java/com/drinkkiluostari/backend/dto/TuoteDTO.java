package com.drinkkiluostari.backend.dto;

import java.util.List;

public record TuoteDTO(String nimi, double hinta, KategoriaDTO kategoria, List<Long> tilausriviIdt) {

}
