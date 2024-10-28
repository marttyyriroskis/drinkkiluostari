package com.drinkkiluostari.backend.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.drinkkiluostari.backend.domain.*;

public interface TuoteRepository extends CrudRepository<Tuote, Long> {
    @Query(value = "SELECT * FROM tuotteet WHERE deleted_at IS NULL", nativeQuery = true)
    List<Tuote> findAllActive();

    @Query(value = "SELECT * FROM tuotteet WHERE id = :id AND deleted_at IS NULL", nativeQuery = true)
    Optional<Tuote> findByIdActive(@Param("id") Long id);
}
