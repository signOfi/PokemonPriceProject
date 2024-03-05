package com.pokemon.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableAsync
public class PokemonPriceTrackerApplication {

	public static void main(String[] args) {
		SpringApplication.run(PokemonPriceTrackerApplication.class, args);
	}

}
