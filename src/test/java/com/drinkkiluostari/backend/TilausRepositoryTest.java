package com.drinkkiluostari.backend;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import java.util.ArrayList;
import java.util.Optional;
import java.time.LocalDateTime;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;

import com.drinkkiluostari.backend.domain.Tilaus;
import com.drinkkiluostari.backend.repository.TilausRepository;
import com.drinkkiluostari.backend.domain.Tyontekija;
import com.drinkkiluostari.backend.repository.TyontekijaRepository;
import com.drinkkiluostari.backend.domain.Asiakas;
import com.drinkkiluostari.backend.repository.AsiakasRepository;
import com.drinkkiluostari.backend.domain.Tilausrivi;
import com.drinkkiluostari.backend.repository.TilausriviRepository;

@SpringBootTest(classes = DrinkkiluostariApplication.class)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class TilausRepositoryTest {
    private final TilausRepository tilausRepository;
    private final TyontekijaRepository tyontekijaRepository;
    private final AsiakasRepository asiakasRepository;
    private final TilausriviRepository tilausriviRepository;
    
    public TilausRepositoryTest(TilausRepository tilausRepository, TyontekijaRepository tyontekijaRepository, AsiakasRepository asiakasRepository,
    TilausriviRepository tilausriviRepository) {
        this.tilausRepository = tilausRepository;
        this.tyontekijaRepository = tyontekijaRepository;
        this.asiakasRepository = asiakasRepository;
        this.tilausriviRepository = tilausriviRepository;
    }

    @Test
    public void findByIdReturnsTilaus() {
        assertThat(tilausRepository.findById(1L).get()).isNotNull();
    }

    @Test
    public void createAndDeleteTilaus() {
        Tyontekija tyontekija = new Tyontekija("Matti", "Meikäläinen", "matti.meikalainen@sahkoposti.fi", "$2a$10$e28oUcAUUCThKlkIKkZkVeKtK6tm1C/Ff0ft7cFdepRqYG8kbHNHC");
        tyontekijaRepository.save(tyontekija);

        Asiakas asiakas = new Asiakas("Meikäläisyritys", "Meikäläisenkatu 1", "00000-0");
        asiakasRepository.save(asiakas);
        
        Tilausrivi tilausrivi = new Tilausrivi(20.00, 0.1, 50);
        tilausriviRepository.save(tilausrivi);

        List<Tilausrivi> tilausrivit = new ArrayList<>();
        tilausrivit.add(tilausrivi);

        Tilaus tilaus = new Tilaus(LocalDateTime.of(2024, 9, 12, 12, 00), tyontekija, asiakas, tilausrivit);
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
