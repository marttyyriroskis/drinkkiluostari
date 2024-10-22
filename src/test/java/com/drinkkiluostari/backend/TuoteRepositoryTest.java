package com.drinkkiluostari.backend;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;

import com.drinkkiluostari.backend.domain.Tuote;
import com.drinkkiluostari.backend.repository.TuoteRepository;

@SpringBootTest(classes = DrinkkiluostariApplication.class)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class TuoteRepositoryTest {
    @Autowired
    private TuoteRepository tuoteRepository;

    @Test
    public void findByIdReturnsTuote() {
        assertThat(tuoteRepository.findById(1L).get()).isNotNull();
    }

    @Test
    public void createAndDeleteTuote() {        
        Tuote tuote = new Tuote("Kalja", 0.60);
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
