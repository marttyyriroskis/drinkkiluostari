package com.drinkkiluostari.backend;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import java.util.ArrayList;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;

import com.drinkkiluostari.backend.domain.Rooli;
import com.drinkkiluostari.backend.repository.RooliRepository;
import com.drinkkiluostari.backend.domain.Tyontekija;
import com.drinkkiluostari.backend.repository.TyontekijaRepository;

@SpringBootTest(classes = DrinkkiluostariApplication.class)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class RooliRepositoryTest {
    private final RooliRepository rooliRepository;
    private final TyontekijaRepository tyontekijaRepository;
    
    public RooliRepositoryTest(RooliRepository rooliRepository, TyontekijaRepository tyontekijaRepository) {
        this.rooliRepository = rooliRepository;
        this.tyontekijaRepository = tyontekijaRepository;
    }

    @Test
    public void findByIdReturnsRooli() {
        assertThat(rooliRepository.findById(1L).get()).isNotNull();
    }

    @Test
    public void createAndDeleteRooli() {
        Tyontekija tyontekija = new Tyontekija("Maija", "Mehiläinen", "maija.mehilainen@sahkoposti.fi", "$2a$10$reVlDE7xaEZ5XKhCrX0IpOIeNzEzMZkqkwKxvTsIhlqAMMIMz1Cci");
        tyontekijaRepository.save(tyontekija);

        List<Tyontekija> tyontekijat = new ArrayList<>();
        tyontekijat.add(tyontekija);

        Rooli rooli = new Rooli("VIERAS", tyontekijat);
        rooliRepository.save(rooli);

        assertThat(rooli.getId()).isNotNull();

        deleteRooli(rooli.getId());

        Optional<Rooli> deletedRooli = rooliRepository.findById(rooli.getId());
        assertThat(deletedRooli).isEmpty();
    }

    public void deleteRooli(Long rooliId) {
        Optional<Rooli> rooli = rooliRepository.findById(rooliId);
        assertThat(rooli).isPresent();

        rooliRepository.delete(rooli.get());
    }

}
