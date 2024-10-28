package com.drinkkiluostari.backend.dto;

import java.util.List;

public record PostinumeroDTO(Long id, String postinumero, String postitoimipaikka, List<Long> asiakasIdt) {

}
