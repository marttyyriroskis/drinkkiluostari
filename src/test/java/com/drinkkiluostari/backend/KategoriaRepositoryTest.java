package com.drinkkiluostari.backend;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;

import com.drinkkiluostari.backend.domain.Kategoria;
import com.drinkkiluostari.backend.repository.KategoriaRepository;

@SpringBootTest(classes = DrinkkiluostariApplication.class)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class KategoriaRepositoryTest {
    @Autowired
    private KategoriaRepository kategoriaRepository;

    @Test
    public void findByIdReturnsKategoria() {
        assertThat(kategoriaRepository.findById(1L).get()).isNotNull();
    }

    @Test
    public void createAndDeleteKategoria() {
        Kategoria kategoria = new Kategoria("Einekset");
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
