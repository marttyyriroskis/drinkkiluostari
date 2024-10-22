package com.drinkkiluostari.backend;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;

import com.drinkkiluostari.backend.domain.Rooli;
import com.drinkkiluostari.backend.repository.RooliRepository;

@SpringBootTest(classes = DrinkkiluostariApplication.class)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class RooliRepositoryTest {
    @Autowired
    private RooliRepository rooliRepository;

    @Test
    public void findByIdReturnsRooli() {
        assertThat(rooliRepository.findById(1L).get()).isNotNull();
    }

    @Test
    public void createAndDeleteRooli() {
        Rooli rooli = new Rooli("VIERAS");
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
