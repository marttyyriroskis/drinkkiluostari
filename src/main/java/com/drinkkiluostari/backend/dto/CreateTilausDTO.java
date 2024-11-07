package com.drinkkiluostari.backend.dto;

import java.time.LocalDateTime;

public record CreateTilausDTO(LocalDateTime pvm, TyontekijaDTO tyontekija, AsiakasDTO asiakas) {

}
