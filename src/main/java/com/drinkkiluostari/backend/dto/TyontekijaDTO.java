package com.drinkkiluostari.backend.dto;

import java.util.List;

public record TyontekijaDTO(String etunimi, String sukunimi, String salasana, String sahkoposti, RooliDTO rooli, List<Long> tilausIdt) {

}
