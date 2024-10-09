package com.drinkkiluostari.backend.repository;

import org.springframework.data.repository.CrudRepository;

import com.drinkkiluostari.backend.domain.*;

public interface PostinumeroRepository extends CrudRepository<Postinumero, String> {
    Postinumero findByPostinumero(String postinumero);
}
