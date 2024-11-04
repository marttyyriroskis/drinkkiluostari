package com.drinkkiluostari.backend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class DrinkkiluostariApplication {
	// USER credentials: juha.juhanen@sahkoposti.fi, kayttaja
	// ADMIN credentials: Marja.Marjanen@sahkoposti.fi, admin

	// private static final Logger log = LoggerFactory.getLogger(DrinkkiluostariApplication.class);

	public static void main(String[] args) {
		SpringApplication.run(DrinkkiluostariApplication.class, args);
	}

	/*
	@Bean
	public CommandLineRunner demo(AsiakasRepository asiakasRepository, KategoriaRepository kategoriaRepository, PostinumeroRepository
	postinumeroRepository, RooliRepository rooliRepository, TilausRepository tilausRepository, TilausriviRepository tilausRiviRepository,
	TuoteRepository tuoteRepository, TyontekijaRepository tyontekijaRepository) {
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
			rooliRepository.save(new Rooli("KAYTTAJA"));
			rooliRepository.save(new Rooli("ADMIN"));

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
			tyontekijaRepository.save(new Tyontekija("Juha", "Juhanen", "juha.juhanen@sahkoposti.fi", "$2a$10$es76Go.W1/f5/Jms3SBvnuVG20qxUXzXq1KYvJUTxYH6PLxABzafO"));
			tyontekijaRepository.save(new Tyontekija("Marja", "Marjanen", "marja.marjanen@sahkoposti.fi", "$2a$10$uZpnGGH1amn9MEKspLG/Q.PUe58g7Ti8It3y9EWNt/1WcEk6GocdO"));
			
			log.info("Adding relationships...");
			Asiakas asiakas1 = asiakasRepository.findById(1L).get();
			Asiakas asiakas2 = asiakasRepository.findById(2L).get();
			asiakas1.setPostinumero(postinumeroRepository.findById(1L).get());
			asiakas2.setPostinumero(postinumeroRepository.findById(2L).get());
			asiakasRepository.save(asiakas1);
			asiakasRepository.save(asiakas2);

			Tilaus tilaus1 = tilausRepository.findById(1L).get();
			Tilaus tilaus2 = tilausRepository.findById(2L).get();
			tilaus1.setTyontekija(tyontekijaRepository.findById(1L).get());
			tilaus2.setTyontekija(tyontekijaRepository.findById(2L).get());
			tilaus1.setAsiakas(asiakas1);
			tilaus2.setAsiakas(asiakas2);
			tilausRepository.save(tilaus1);
			tilausRepository.save(tilaus2);

			Tilausrivi tilausrivi1 = tilausRiviRepository.findById(1L).get();
			Tilausrivi tilausrivi2 = tilausRiviRepository.findById(2L).get();
			tilausrivi1.setTuote(tuoteRepository.findById(1L).get());
			tilausrivi2.setTuote(tuoteRepository.findById(2L).get());
			tilausrivi1.setTilaus(tilaus1);
			tilausrivi2.setTilaus(tilaus2);
			tilausRiviRepository.save(tilausrivi1);
			tilausRiviRepository.save(tilausrivi2);

			Kategoria kategoria1 = kategoriaRepository.findById(1L).get();
			Kategoria kategoria2 = kategoriaRepository.findById(2L).get();
			kategoriaRepository.save(kategoria1);
			kategoriaRepository.save(kategoria2);

			Tuote tuote1 = tuoteRepository.findById(1L).get();
			Tuote tuote2 = tuoteRepository.findById(2L).get();
			tuote1.setKategoria(kategoria1);
			tuote2.setKategoria(kategoria2);
			tuoteRepository.save(tuote1);
			tuoteRepository.save(tuote2);

			Rooli rooli1 = rooliRepository.findById(1L).get();
			Rooli rooli2 = rooliRepository.findById(2L).get();
			rooliRepository.save(rooli1);
			rooliRepository.save(rooli2);

			Tyontekija tyontekija1 = tyontekijaRepository.findById(1L).get();
			Tyontekija tyontekija2 = tyontekijaRepository.findById(2L).get();
			tyontekija1.setRooli(rooli1);
			tyontekija2.setRooli(rooli2);
			tyontekijaRepository.save(tyontekija1);
			tyontekijaRepository.save(tyontekija2);
		};
	}
*/
}
