package com.drinkkiluostari.backend.dto;

import java.util.List;

public record KategoriaDTO(Long id, String nimi, List<Long> tuoteIdt) {

}
