package com.talk2amareswaran.projects.ssedemo;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import reactor.core.publisher.Flux;

@RestController
public class CoinRestController {

	private List<Coins> coinsList = new ArrayList<>();

	@Autowired
	CoinEventService coinEventService;

	@Bean
	CommandLineRunner commandLineRunner() {
		return args -> {
			initializeCoins();
		};
	}

	private void initializeCoins() {
		Coins coin1 = new Coins("Bitcoin", "$ " + Math.round(100 + (1000 - 100) * new Random().nextDouble()) + " USD");
		Coins coin2 = new Coins("Litecoin", "$ " + Math.round(100 + (1000 - 100) * new Random().nextDouble()) + " USD");
		coinsList.add(coin1);
		coinsList.add(coin2);
	}

	@RequestMapping(value = "/coins/{country}", produces = MediaType.TEXT_EVENT_STREAM_VALUE, method = RequestMethod.GET)
	public Flux<List<Coins>> coinEvents(@PathVariable String country) {
		return coinEventService.getCoinsEvents(coinsList, country);
	}
}