package com.avenoir.microservices.apiGateway.customRoutes;

import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ApiGatewayConfiguration { 
	@Bean
	public RouteLocator gatewayRouter(RouteLocatorBuilder builder) {
//		Function<PredicateSpec, Buildable<Route>> routeFunction
//		= p-> p.path("/get")
//				.uri("http://httpbin.org:80");
		return builder.routes()
				.route(p-> p.path("/get")
						.filters(f-> f
								 .addRequestHeader("Myheader", "MyURI")
								 .addRequestParameter("Param", "OurValue"))
						.uri("http://httpbin.org:80"))
				.route(p->p.path("/currency-exchange/**")
						.uri("lb://currency-exchange")) //redirect using naming server the display currency-exchange registeration and also do the load balancing
				.route(p->p.path("/currency-conversion/**")
						.uri("lb://currency-conversion"))
				.route(p->p.path("/currency-conversion-feign/**")
						.uri("lb://currency-conversion"))
				.route(p->p.path("/currency-conversion-new/**")
						.filters(f->f.rewritePath("/currency-conversion-new/(?<segment>.*)", 
								"/currency-conversion-feign/${segment}"))
						.uri("lb://currency-conversion"))
				.build();
	}
}
