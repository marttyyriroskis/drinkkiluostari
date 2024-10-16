package com.drinkkiluostari.backend.repository;

import org.springframework.data.repository.CrudRepository;

import com.drinkkiluostari.backend.domain.*;

public interface TyontekijaRepository extends CrudRepository<Tyontekija, Long> {
    Tyontekija findBySahkoposti(String sahkoposti);
}
