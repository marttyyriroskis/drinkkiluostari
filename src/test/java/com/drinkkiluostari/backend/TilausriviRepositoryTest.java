package com.drinkkiluostari.backend;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Optional;
import java.time.LocalDateTime;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;

import com.drinkkiluostari.backend.domain.Tilaus;
import com.drinkkiluostari.backend.repository.TilausRepository;
import com.drinkkiluostari.backend.domain.Tuote;
import com.drinkkiluostari.backend.repository.TuoteRepository;
import com.drinkkiluostari.backend.domain.Tilausrivi;
import com.drinkkiluostari.backend.repository.TilausriviRepository;

@SpringBootTest(classes = DrinkkiluostariApplication.class)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class TilausriviRepositoryTest {
    private final TilausriviRepository tilausriviRepository;
    private final TilausRepository tilausRepository;
    private final TuoteRepository tuoteRepository;
    
    public TilausriviRepositoryTest(TilausriviRepository tilausriviRepository, TilausRepository tilausRepository, TuoteRepository tuoteRepository) {
        this.tilausriviRepository = tilausriviRepository;
        this.tilausRepository = tilausRepository;
        this.tuoteRepository = tuoteRepository;      
    }

    @Test
    public void findByIdReturnsTilausrivi() {
        assertThat(tilausriviRepository.findById(1L).get()).isNotNull();
    }

    @Test
    public void createAndDeleteTilausrivi() {        
        Tilaus tilaus = new Tilaus(LocalDateTime.of(2024, 9, 12, 12, 00));
        tilausRepository.save(tilaus);

        Tuote tuote = new Tuote("Riisi", 0.60);
        tuoteRepository.save(tuote);

        Tilausrivi tilausrivi = new Tilausrivi(20.00, 0.1, 50, tuote, tilaus);
        tilausriviRepository.save(tilausrivi);

        assertThat(tilausrivi.getId()).isNotNull();

        deleteTilausrivi(tilausrivi.getId());

        Optional<Tilausrivi> deletedTilausrivi = tilausriviRepository.findById(tilausrivi.getId());
        assertThat(deletedTilausrivi).isEmpty();
    }

    public void deleteTilausrivi(Long tilausriviId) {
        Optional<Tilausrivi> tilausrivi = tilausriviRepository.findById(tilausriviId);
        assertThat(tilausrivi).isPresent();

        tilausriviRepository.delete(tilausrivi.get());
    }

}
