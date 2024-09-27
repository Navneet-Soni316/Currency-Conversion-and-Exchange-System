package com.avenoir.microservices.currencyconversionservice.proxy;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.avenoir.microservices.currencyconversionservice.entity.CurrencyConversion;


//@FeignClient(name = "currency-exchange",url ="localhost:8000")
//@FeignClient(name = "application-name given in app.properties of called micros ",
//url ="called micros i.e localhost:8000")
@FeignClient(name = "currency-exchange") //to make loadbalancing between multiple instancesdon't use url attribute

public interface CurrencyExchangeProxy {
	@GetMapping("/currency-exchange/from/{from}/to/{to}")
	public CurrencyConversion retrieveExchangeValue(@PathVariable String from,
				@PathVariable String to);

}
