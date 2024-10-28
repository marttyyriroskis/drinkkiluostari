package com.drinkkiluostari.backend.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.drinkkiluostari.backend.domain.*;

public interface TilausRepository extends CrudRepository<Tilaus, Long> {
    @Query(value = "SELECT * FROM tilaukset WHERE deleted_at IS NULL", nativeQuery = true)
    List<Tilaus> findAllActive();

    @Query(value = "SELECT * FROM tilaukset WHERE id = :id AND deleted_at IS NULL", nativeQuery = true)
    Optional<Tilaus> findByIdActive(@Param("id") Long id);
}
