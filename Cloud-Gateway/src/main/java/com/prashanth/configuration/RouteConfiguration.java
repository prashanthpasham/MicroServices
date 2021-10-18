package com.prashanth.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RouteConfiguration {

	@Autowired
	private SpringCloudFilter cloudFilter;

	@Bean
	public RouteLocator getRoutes(RouteLocatorBuilder builder) {
		return builder.routes()
				.route("school-api",
						r -> r.path("/school/**").filters(f -> f.filter(cloudFilter)).uri("lb://school-micro"))
				.build();

	}

}
