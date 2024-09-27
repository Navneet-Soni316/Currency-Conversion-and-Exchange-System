package com.avenoir.microservices.currencyconversionservice.controller;

import java.math.BigDecimal;
import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.avenoir.microservices.currencyconversionservice.entity.CurrencyConversion;
import com.avenoir.microservices.currencyconversionservice.proxy.CurrencyExchangeProxy;

@RestController
public class CurrencyConversionController {
	@Autowired
	private CurrencyExchangeProxy proxy;
	//http://localhost:8100/currency-conversion/from/USD/to/INR/quantity/10
	@GetMapping("/currency-conversion/from/{from}/to/{to}/quantity/{quantity}")
	public CurrencyConversion calculateCurrencyConversion(@PathVariable String from,
				@PathVariable String to, @PathVariable BigDecimal quantity) {
		HashMap<String, String> uriVar= new HashMap<>();
		uriVar.put("from", from);
		uriVar.put("to", to);
	    ResponseEntity<CurrencyConversion> responseEntity =	new RestTemplate().getForEntity("http://localhost:8000/currency-exchange/from/{from}/to/{to}",
	    														CurrencyConversion.class,uriVar);
	    CurrencyConversion currencyConversion	= responseEntity.getBody();
		return new CurrencyConversion(currencyConversion.getId(), from, to, quantity,
							currencyConversion.getConversionMultiple(),
							quantity.multiply(currencyConversion.getConversionMultiple()),currencyConversion.getEnvironment()+" "+"rest Template");
	}
	
	@GetMapping("/currency-conversion-feign/from/{from}/to/{to}/quantity/{quantity}")
	public CurrencyConversion calculateCurrencyConversionFeign(
				@PathVariable String from,
				@PathVariable String to,
				@PathVariable BigDecimal quantity
				) {
		
	    CurrencyConversion currencyConversion	= proxy.retrieveExchangeValue(from, to);
		return new CurrencyConversion(currencyConversion.getId(), from, to, quantity,
							currencyConversion.getConversionMultiple(),
							quantity.multiply(currencyConversion.getConversionMultiple()),currencyConversion.getEnvironment()+" "+"Feign");
	}
	
	
}
