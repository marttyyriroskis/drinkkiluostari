package com.drinkkiluostari.backend;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;

import com.drinkkiluostari.backend.domain.Rooli;
import com.drinkkiluostari.backend.domain.Tilaus;
import com.drinkkiluostari.backend.repository.RooliRepository;
import com.drinkkiluostari.backend.repository.TilausRepository;
import com.drinkkiluostari.backend.domain.Tyontekija;
import com.drinkkiluostari.backend.repository.TyontekijaRepository;

@SpringBootTest(classes = DrinkkiluostariApplication.class)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class TyontekijaRepositoryTest {
    private final TyontekijaRepository tyontekijaRepository;
    private final RooliRepository rooliRepository;
    private final TilausRepository tilausRepository;

    public TyontekijaRepositoryTest(TyontekijaRepository tyontekijaRepository, TilausRepository tilausRepository, RooliRepository rooliRepository) {
        this.tyontekijaRepository = tyontekijaRepository;
        this.rooliRepository = rooliRepository;
        this.tilausRepository = tilausRepository;
    }

    @Test
    public void findByIdReturnsTyontekija() {
        assertThat(tyontekijaRepository.findById(1L).get()).isNotNull();
    }

    @Test
    public void createAndDeleteTuote() {        
        Rooli rooli = new Rooli("KONSULTTI");
        rooliRepository.save(rooli);
        
        Tilaus tilaus = new Tilaus(LocalDateTime.of(2024, 1, 20, 9, 00));
        tilausRepository.save(tilaus);

        List<Tilaus> tilaukset = new ArrayList<>();
        tilaukset.add(tilaus);

        Tyontekija tyontekija = new Tyontekija("Mikko", "Mikonpoika", "mikko.mikonpoika@sahkoposti.fi", "generoi_salasana", rooli, tilaukset);
        tyontekijaRepository.save(tyontekija);

        assertThat(tyontekija.getId()).isNotNull();

        deleteTyontekija(tyontekija.getId());

        Optional<Tyontekija> deletedTyontekija = tyontekijaRepository.findById(tyontekija.getId());
        assertThat(deletedTyontekija).isEmpty();
    }

    public void deleteTyontekija(Long tyontekijaId) {
        Optional<Tyontekija> tyontekija = tyontekijaRepository.findById(tyontekijaId);
        assertThat(tyontekija).isPresent();

        tyontekijaRepository.delete(tyontekija.get());
    }

}
