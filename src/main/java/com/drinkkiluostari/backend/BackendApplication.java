package com.drinkkiluostari.backend;

import java.time.LocalDateTime;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import com.drinkkiluostari.backend.domain.Asiakas;
import com.drinkkiluostari.backend.domain.Kategoria;
import com.drinkkiluostari.backend.domain.Postinumero;
import com.drinkkiluostari.backend.domain.Rooli;
import com.drinkkiluostari.backend.domain.Tilaus;
import com.drinkkiluostari.backend.domain.Tilausrivi;
import com.drinkkiluostari.backend.domain.Tuote;
import com.drinkkiluostari.backend.domain.Tyontekija;
import com.drinkkiluostari.backend.repository.AsiakasRepository;
import com.drinkkiluostari.backend.repository.KategoriaRepository;
import com.drinkkiluostari.backend.repository.PostinumeroRepository;
import com.drinkkiluostari.backend.repository.RooliRepository;
import com.drinkkiluostari.backend.repository.TilausRepository;
import com.drinkkiluostari.backend.repository.TilausriviRepository;
import com.drinkkiluostari.backend.repository.TuoteRepository;
import com.drinkkiluostari.backend.repository.TyontekijaRepository;

@SpringBootApplication
public class BackendApplication {

	private static final Logger log = LoggerFactory.getLogger(BackendApplication.class);

	public static void main(String[] args) {
		SpringApplication.run(BackendApplication.class, args);
	}

	@Bean
	public CommandLineRunner demo(AsiakasRepository asiakasRepository, KategoriaRepository kategoriaRepository, PostinumeroRepository
	postinumeroRepository, RooliRepository rooliRepository, TilausRepository tilausRepository, TilausriviRepository tilausRiviRepository,
	TuoteRepository tuoteRepository, TyontekijaRepository tyontekijaRepository) {
		// TODO: Make test entries with max constructors
		return (args) -> {
			log.info("Creating asiakas test entries");
			asiakasRepository.save(new Asiakas("Asiakas", "Asiakkaankatu 1", "333"));
			asiakasRepository.save(new Asiakas("Yritys", "Kadunkatu 1", "666"));

			log.info("Creating kategoria test entries");
			kategoriaRepository.save(new Kategoria("Perunat"));
			kategoriaRepository.save(new Kategoria("Taatelit"));

			log.info("Creating postinumero test entries");
			postinumeroRepository.save(new Postinumero("00100", "Helsinki"));
			postinumeroRepository.save(new Postinumero("01600", "Vantaa"));

			log.info("Creating rooli test entries");
			rooliRepository.save(new Rooli("Käyttäjä"));
			rooliRepository.save(new Rooli("Admin"));

			log.info("Creating tilaus test entries");
			tilausRepository.save(new Tilaus(LocalDateTime.of(2024, 9, 12, 12, 00)));
			tilausRepository.save(new Tilaus(LocalDateTime.of(2024, 3, 29, 12, 00)));
			
			log.info("Creating tilausrivi test entries");
			tilausRiviRepository.save(new Tilausrivi(10.00, 0.1, 100));
			tilausRiviRepository.save(new Tilausrivi(20.00, 0.2, 50));
			
			log.info("Creating tuote test entries");
			tuoteRepository.save(new Tuote("Peruna", 0.60));
			tuoteRepository.save(new Tuote("Taateli", 6.0));

			log.info("Creating tyontekija test entries");
			tyontekijaRepository.save(new Tyontekija("Juha", "Juhanen", "juha.juhanen@sahkoposti.fi", "kayttaja"));
			tyontekijaRepository.save(new Tyontekija("Marja", "Marjanen", "marja.marjanen@sahkoposti.fi", "admin"));
		};
	}

}
