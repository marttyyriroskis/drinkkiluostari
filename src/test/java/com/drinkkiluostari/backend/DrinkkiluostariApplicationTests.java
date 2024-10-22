package com.drinkkiluostari.backend;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.drinkkiluostari.backend.web.AsiakasController;
import com.drinkkiluostari.backend.web.TilausController;
import com.drinkkiluostari.backend.web.TuoteController;
import com.drinkkiluostari.backend.web.TyontekijaController;

@SpringBootTest
class DrinkkiluostariApplicationTests {

	@Autowired
	private AsiakasController asiakasController;

	@Autowired
    private TilausController tilausController;

	@Autowired
    private TuoteController tuoteController;
	
	@Autowired
	private TyontekijaController tyontekijaController;

	@Test
	void contextLoadsAsiakas() throws Exception {
		assertThat(asiakasController).isNotNull();
	}

	@Test
	void contextLoadsTilaus() throws Exception {
		assertThat(tilausController).isNotNull();
	}

	@Test
	void contextLoadsTuote() throws Exception {
		assertThat(tuoteController).isNotNull();
	}

	@Test
	void contextLoadsTyontekija() throws Exception {
		assertThat(tyontekijaController).isNotNull();
	}

}
