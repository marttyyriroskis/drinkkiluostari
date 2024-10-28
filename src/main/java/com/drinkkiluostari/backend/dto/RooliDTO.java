package com.drinkkiluostari.backend.dto;

import java.util.List;

public record RooliDTO(Long id, String nimi, List<Long> tyontekijaIdt) {

}
