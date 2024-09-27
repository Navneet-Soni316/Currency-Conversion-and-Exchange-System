package com.avenoir.microservices.currencyexchangeservice.controller;

import java.math.BigDecimal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.avenoir.microservices.currencyexchangeservice.entity.CurrencyExchange;
import com.avenoir.microservices.currencyexchangeservice.repo.CurrencyExchangeRepository;

@RestController
public class CurrencyExchangeController {
	@Autowired
	private Environment environment;
	@Autowired
	private CurrencyExchangeRepository repository;
	//http://localhost:8000/currency-exchange/from/USD/to/INR
	@GetMapping("/currency-exchange/from/{from}/to/{to}")
	public CurrencyExchange retrieveExchangeValue(@PathVariable String from ,@PathVariable String to) {
//		CurrencyExchange currencyExchange = new CurrencyExchange(1000l,from,to, BigDecimal.valueOf(50));
		CurrencyExchange currencyExchange = repository.findByFromAndTo(from, to);
		if(currencyExchange == null)
			throw new RuntimeException("Unable to find the data");
		String port = environment.getProperty("local.server.port");
		currencyExchange.setEnvironment(port);
		return  currencyExchange;
	}
}
