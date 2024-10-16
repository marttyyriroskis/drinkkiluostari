package com.drinkkiluostari.backend;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import com.drinkkiluostari.backend.web.AsiakasController;
import com.drinkkiluostari.backend.web.TilausController;
import com.drinkkiluostari.backend.web.TuoteController;
import com.drinkkiluostari.backend.web.TyontekijaController;

@SpringBootTest
class DrinkkiluostariApplicationTests {

	private final AsiakasController asiakasController;
    private final TilausController tilausController;
    private final TuoteController tuoteController;
	private final TyontekijaController tyontekijaController;
    
    public DrinkkiluostariApplicationTests(AsiakasController asiakasController, TilausController tilausController,
	TuoteController tuoteController, TyontekijaController tyontekijaController) {
        this.asiakasController = asiakasController;
        this.tilausController = tilausController;
        this.tuoteController = tuoteController;
		this.tyontekijaController = tyontekijaController;
    }

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
