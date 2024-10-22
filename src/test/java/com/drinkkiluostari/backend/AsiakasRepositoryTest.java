package com.drinkkiluostari.backend;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;

import com.drinkkiluostari.backend.domain.Asiakas;
import com.drinkkiluostari.backend.repository.AsiakasRepository;

@SpringBootTest(classes = DrinkkiluostariApplication.class)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class AsiakasRepositoryTest {
    @Autowired
    private AsiakasRepository asiakasRepository;

    @Test
    public void findByIdReturnsAsiakas() {
        assertThat(asiakasRepository.findById(1L).get()).isNotNull();
    }

    @Test
    public void createAndDeleteAsiakas() {
        Asiakas asiakas = new Asiakas("Asiakasyritys", "Asiakasyrityksenkatu 1", "12345-6");
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
