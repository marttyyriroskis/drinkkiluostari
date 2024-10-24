package com.drinkkiluostari.backend.dto;

import java.time.LocalDateTime;

import java.util.List;

public record TilausDTO(LocalDateTime pvm, TyontekijaDTO tyontekija, AsiakasDTO asiakas, List<Long> tilausriviIdt) {
    
}
