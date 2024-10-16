package com.drinkkiluostari.backend;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import java.util.ArrayList;
import java.util.Optional;
import java.time.LocalDateTime;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;

import com.drinkkiluostari.backend.domain.Asiakas;
import com.drinkkiluostari.backend.repository.AsiakasRepository;
import com.drinkkiluostari.backend.domain.Postinumero;
import com.drinkkiluostari.backend.repository.PostinumeroRepository;
import com.drinkkiluostari.backend.domain.Tilaus;
import com.drinkkiluostari.backend.repository.TilausRepository;

@SpringBootTest(classes = DrinkkiluostariApplication.class)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class AsiakasRepositoryTest {
    private final AsiakasRepository asiakasRepository;
    private final PostinumeroRepository postinumeroRepository;
    private final TilausRepository tilausRepository;
    
    public AsiakasRepositoryTest(AsiakasRepository asiakasRepository, PostinumeroRepository postinumeroRepository, TilausRepository tilausRepository) {
        this.asiakasRepository = asiakasRepository;
        this.postinumeroRepository = postinumeroRepository;
        this.tilausRepository = tilausRepository;
    }

    @Test
    public void findByIdReturnsAsiakas() {
        assertThat(asiakasRepository.findById(1L).get()).isNotNull();
    }

    @Test
    public void createAndDeleteAsiakas() {
        Postinumero postinumero = new Postinumero("36500", "Kouvola");
        postinumeroRepository.save(postinumero);

        Tilaus tilaus = new Tilaus(LocalDateTime.of(2024, 1, 20, 9, 00));
        tilausRepository.save(tilaus);

        List<Tilaus> tilaukset = new ArrayList<>();
        tilaukset.add(tilaus);

        Asiakas asiakas = new Asiakas("Asiakasyritys", "Asiakasyrityksenkatu 1", "12345-6", postinumero, tilaukset);
        asiakasRepository.save(asiakas);

        assertThat(asiakas.getId()).isNotNull();

        deleteAsiakas(asiakas.getId());

        Optional<Asiakas> deletedAsiakas = asiakasRepository.findById(asiakas.getId());
        assertThat(deletedAsiakas).isEmpty();
    }

    public void deleteAsiakas(Long asiakasId) {
        Optional<Asiakas> asiakas = asiakasRepository.findById(asiakasId);
        assertThat(asiakas).isPresent();

        asiakasRepository.delete(asiakas.get());
    }

}
