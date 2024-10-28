package com.drinkkiluostari.backend.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.drinkkiluostari.backend.domain.*;

public interface TyontekijaRepository extends CrudRepository<Tyontekija, Long> {
    @Query(value = "SELECT * FROM tyontekijat WHERE deleted_at IS NULL", nativeQuery = true)
    List<Tyontekija> findAllActive();

    @Query(value = "SELECT * FROM tyontekijat WHERE id = :id AND deleted_at IS NULL", nativeQuery = true)
    Optional<Tyontekija> findByIdActive(@Param("id") Long id);
    
    Tyontekija findBySahkoposti(String sahkoposti);
}
