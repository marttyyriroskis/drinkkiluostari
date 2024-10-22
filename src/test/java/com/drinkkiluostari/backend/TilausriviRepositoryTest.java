package com.drinkkiluostari.backend;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;

import com.drinkkiluostari.backend.domain.Tilausrivi;
import com.drinkkiluostari.backend.repository.TilausriviRepository;

@SpringBootTest(classes = DrinkkiluostariApplication.class)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class TilausriviRepositoryTest {
    @Autowired
    private TilausriviRepository tilausriviRepository;

    @Test
    public void findByIdReturnsTilausrivi() {
        assertThat(tilausriviRepository.findById(1L).get()).isNotNull();
    }

    @Test
    public void createAndDeleteTilausrivi() {        
        Tilausrivi tilausrivi = new Tilausrivi(20.00, 0.1, 50);
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
