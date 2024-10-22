package com.drinkkiluostari.backend;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;

import com.drinkkiluostari.backend.domain.Postinumero;
import com.drinkkiluostari.backend.repository.PostinumeroRepository;

@SpringBootTest(classes = DrinkkiluostariApplication.class)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class PostinumeroRepositoryTest {
    @Autowired
    private PostinumeroRepository postinumeroRepository;

    @Test
    public void findByIdReturnsPostinumero() {
        assertThat(postinumeroRepository.findById(1L).get()).isNotNull();
    }

    @Test
    public void createAndDeletePostinumero() {
        Postinumero postinumero = new Postinumero("00110", "Helsinki");
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
