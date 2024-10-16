package com.drinkkiluostari.backend;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import java.util.ArrayList;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;

import com.drinkkiluostari.backend.domain.Kategoria;
import com.drinkkiluostari.backend.repository.KategoriaRepository;
import com.drinkkiluostari.backend.domain.Tuote;
import com.drinkkiluostari.backend.repository.TuoteRepository;

@SpringBootTest(classes = DrinkkiluostariApplication.class)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class KategoriaRepositoryTest {
    private final KategoriaRepository kategoriaRepository;
    private final TuoteRepository tuoteRepository;
    
    public KategoriaRepositoryTest(KategoriaRepository kategoriaRepository, TuoteRepository tuoteRepository) {
        this.kategoriaRepository = kategoriaRepository;
        this.tuoteRepository = tuoteRepository;
    }

    @Test
    public void findByIdReturnsKategoria() {
        assertThat(kategoriaRepository.findById(1L).get()).isNotNull();
    }

    @Test
    public void createAndDeleteKategoria() {
        Tuote tuote = new Tuote("Lihapulla", 3.60);
        tuoteRepository.save(tuote);

        List<Tuote> tuotteet = new ArrayList<>();
        tuotteet.add(tuote);

        Kategoria kategoria = new Kategoria("Einekset", tuotteet);
        kategoriaRepository.save(kategoria);

        assertThat(kategoria.getId()).isNotNull();

        deleteKategoria(kategoria.getId());

        Optional<Kategoria> deletedKategoria = kategoriaRepository.findById(kategoria.getId());
        assertThat(deletedKategoria).isEmpty();
    }

    public void deleteKategoria(Long kategoriaId) {
        Optional<Kategoria> kategoria = kategoriaRepository.findById(kategoriaId);
        assertThat(kategoria).isPresent();

        kategoriaRepository.delete(kategoria.get());
    }
    
}
