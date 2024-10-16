package com.drinkkiluostari.backend;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import java.util.ArrayList;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;

import com.drinkkiluostari.backend.domain.Asiakas;
import com.drinkkiluostari.backend.repository.AsiakasRepository;
import com.drinkkiluostari.backend.domain.Postinumero;
import com.drinkkiluostari.backend.repository.PostinumeroRepository;

@SpringBootTest(classes = DrinkkiluostariApplication.class)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class PostinumeroRepositoryTest {
    private final PostinumeroRepository postinumeroRepository;
    private final AsiakasRepository asiakasRepository;
    
    public PostinumeroRepositoryTest(PostinumeroRepository postinumeroRepository, AsiakasRepository asiakasRepository) {
        this.postinumeroRepository = postinumeroRepository;
        this.asiakasRepository = asiakasRepository;
    }

    @Test
    public void findByIdReturnsPostinumero() {
        assertThat(postinumeroRepository.findById(1L).get()).isNotNull();
    }

    @Test
    public void createAndDeletePostinumero() {
        Asiakas asiakas = new Asiakas("Työmaa", "Työmaankatu 1", "123123-1");
        asiakasRepository.save(asiakas);

        List<Asiakas> asiakkaat = new ArrayList<>();
        asiakkaat.add(asiakas);

        Postinumero postinumero = new Postinumero("00110", "Helsinki", asiakkaat);
        postinumeroRepository.save(postinumero);

        assertThat(postinumero.getId()).isNotNull();

        deletePostinumero(postinumero.getId());

        Optional<Postinumero> deletedPostinumero = postinumeroRepository.findById(postinumero.getId());
        assertThat(deletedPostinumero).isEmpty();
    }

    public void deletePostinumero(Long postinumeroId) {
        Optional<Postinumero> postinumero = postinumeroRepository.findById(postinumeroId);
        assertThat(postinumero).isPresent();

        postinumeroRepository.delete(postinumero.get());
    }

}
