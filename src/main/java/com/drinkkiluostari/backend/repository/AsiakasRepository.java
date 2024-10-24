package com.drinkkiluostari.backend.repository;

import java.util.Optional;
import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.drinkkiluostari.backend.domain.*;

public interface AsiakasRepository extends CrudRepository<Asiakas, Long> {
    @Query(value = "SELECT * FROM asiakkaat WHERE deleted_at IS NULL", nativeQuery = true)
    List<Asiakas> findAllActive();

    @Query(value = "SELECT * FROM asiakkaat WHERE id = :id AND deleted_at IS NULL", nativeQuery = true)
    Optional<Asiakas> findByIdActive(@Param("id") Long id);
}
