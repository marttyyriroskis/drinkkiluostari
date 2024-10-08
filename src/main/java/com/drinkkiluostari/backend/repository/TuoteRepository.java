package com.drinkkiluostari.backend.repository;

import org.springframework.data.repository.CrudRepository;

import com.drinkkiluostari.backend.domain.*;

public interface TuoteRepository extends CrudRepository<Tuote, Long> {

}
