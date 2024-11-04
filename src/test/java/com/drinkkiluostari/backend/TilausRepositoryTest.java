package com.drinkkiluostari.backend;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;

import com.drinkkiluostari.backend.domain.Tilaus;
import com.drinkkiluostari.backend.repository.TilausRepository;

@SpringBootTest(classes = DrinkkiluostariApplication.class)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class TilausRepositoryTest {
    @Autowired
    private TilausRepository tilausRepository;

    @Test
    public void findByIdReturnsTilaus() {
        assertThat(tilausRepository.findById(1L).get()).isNotNull();
    }

    @Test
    public void createAndDeleteTilaus() {
        Tilaus tilaus = new Tilaus();
        tilaus.create();
        tilausRepository.save(tilaus);

        assertThat(tilaus.getId()).isNotNull();

        deleteTilaus(tilaus.getId());

        Optional<Tilaus> deletedTilaus = tilausRepository.findById(tilaus.getId());
        assertThat(deletedTilaus).isEmpty();
    }

    public void deleteTilaus(Long tilausId) {
        Optional<Tilaus> tilaus = tilausRepository.findById(tilausId);
        assertThat(tilaus).isPresent();

        tilausRepository.delete(tilaus.get());
    }

}
