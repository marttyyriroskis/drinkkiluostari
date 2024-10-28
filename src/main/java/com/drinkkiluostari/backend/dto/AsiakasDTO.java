package com.drinkkiluostari.backend.dto;

import java.util.List;

public record AsiakasDTO(Long id, String nimi, String katuosoite, String yTunnus, PostinumeroDTO postinumero, List<Long> tilausIdt) {

}
