package com.drinkkiluostari.backend;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;

import com.drinkkiluostari.backend.domain.Kategoria;
import com.drinkkiluostari.backend.repository.KategoriaRepository;
import com.drinkkiluostari.backend.domain.Tuote;
import com.drinkkiluostari.backend.repository.TuoteRepository;
import com.drinkkiluostari.backend.domain.Tilausrivi;
import com.drinkkiluostari.backend.repository.TilausriviRepository;

@SpringBootTest(classes = DrinkkiluostariApplication.class)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class TuoteRepositoryTest {
    private final TuoteRepository tuoteRepository;
    private final KategoriaRepository kategoriaRepository;
    private final TilausriviRepository tilausriviRepository;
    
    public TuoteRepositoryTest(TuoteRepository tuoteRepository, KategoriaRepository kategoriaRepository, TilausriviRepository
    tilausriviRepository) {
        this.tuoteRepository = tuoteRepository;
        this.kategoriaRepository = kategoriaRepository;
        this.tilausriviRepository = tilausriviRepository;
    }

    @Test
    public void findByIdReturnsTuote() {
        assertThat(tuoteRepository.findById(1L).get()).isNotNull();
    }

    @Test
    public void createAndDeleteTuote() {        
        Kategoria kategoria = new Kategoria("Juomat");
        kategoriaRepository.save(kategoria);

        Tilausrivi tilausrivi = new Tilausrivi(10.00, 0.1, 50);
        tilausriviRepository.save(tilausrivi);

        List<Tilausrivi> tilausrivit = new ArrayList<>();
        tilausrivit.add(tilausrivi);

        Tuote tuote = new Tuote("Kalja", 0.60, kategoria, tilausrivit);
        tuoteRepository.save(tuote);

        assertThat(tuote.getId()).isNotNull();

        deleteTuote(tuote.getId());

        Optional<Tuote> deletedTuote = tuoteRepository.findById(tuote.getId());
        assertThat(deletedTuote).isEmpty();
    }

    public void deleteTuote(Long tuoteId) {
        Optional<Tuote> tuote = tuoteRepository.findById(tuoteId);
        assertThat(tuote).isPresent();

        tuoteRepository.delete(tuote.get());
    }

}
