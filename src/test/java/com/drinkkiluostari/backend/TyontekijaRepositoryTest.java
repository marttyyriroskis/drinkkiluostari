package com.drinkkiluostari.backend;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;

import com.drinkkiluostari.backend.domain.Tyontekija;
import com.drinkkiluostari.backend.repository.TyontekijaRepository;

@SpringBootTest(classes = DrinkkiluostariApplication.class)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class TyontekijaRepositoryTest {
    @Autowired
    private TyontekijaRepository tyontekijaRepository;

    @Test
    public void findByIdReturnsTyontekija() {
        assertThat(tyontekijaRepository.findById(1L).get()).isNotNull();
    }

    @Test
    public void createAndDeleteTuote() {        
        Tyontekija tyontekija = new Tyontekija("Mikko", "Mikonpoika", "mikko.mikonpoika@sahkoposti.fi", "generoi_salasana");
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
